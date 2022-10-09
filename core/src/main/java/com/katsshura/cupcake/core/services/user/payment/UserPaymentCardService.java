package com.katsshura.cupcake.core.services.user.payment;

import com.katsshura.cupcake.core.dto.user.payment.UserPaymentCardDTO;
import com.katsshura.cupcake.core.exceptions.NotFoundException;
import com.katsshura.cupcake.core.mapper.user.payment.UserPaymentCardMapper;
import com.katsshura.cupcake.core.repositories.user.UserRepository;
import com.katsshura.cupcake.core.repositories.user.payment.UserPaymentCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserPaymentCardService {

    private final UserPaymentCardRepository userPaymentCardRepository;
    private final UserPaymentCardMapper userPaymentCardMapper;

    private final UserRepository userRepository;

    public UserPaymentCardService(final UserPaymentCardRepository userPaymentCardRepository,
                                  final UserPaymentCardMapper userPaymentCardMapper,
                                  final UserRepository userRepository) {
        this.userPaymentCardRepository = userPaymentCardRepository;
        this.userPaymentCardMapper = userPaymentCardMapper;
        this.userRepository = userRepository;
    }

    public UserPaymentCardDTO saveNewPaymentCard(final UserPaymentCardDTO userPaymentCardDTO,
                                                 final Long userId) {
        final var user = this.userRepository.findById(userId);

        if (user.isEmpty()) {
            log.error("Could not save payment, user with id: [{}] not found", userId);
            throw new NotFoundException(String.format("User with id: [%s]", userId));
        }

        final var paymentEntity = this.userPaymentCardMapper.toEntity(userPaymentCardDTO);
        paymentEntity.setUser(user.get());

        final var result = this.userPaymentCardRepository.save(paymentEntity);
        return this.userPaymentCardMapper.toDTO(result);
    }

    @Transactional
    public List<UserPaymentCardDTO> getUserPayments(final Long userId) {
        return this.userPaymentCardRepository
                .findAllByUserId(userId)
                .stream()
                .map(this.userPaymentCardMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deletePayment(final Long paymentId, final Long userId) {
        final var payment = this.userPaymentCardRepository.findById(paymentId);

        if (payment.isEmpty()) {
            log.error("Could not delete payment,no payment found with id: [{}]!", paymentId);
            throw new NotFoundException(String.format("Payment with id: [%s]", paymentId));
        }

        if (!payment.get().getUser().getId().equals(userId)) return;

        this.userPaymentCardRepository.delete(payment.get());
    }
}
