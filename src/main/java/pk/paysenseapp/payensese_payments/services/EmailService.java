package pk.paysenseapp.payensese_payments.services;

import pk.paysenseapp.payensese_payments.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
