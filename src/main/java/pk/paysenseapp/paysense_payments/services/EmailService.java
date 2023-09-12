package pk.paysenseapp.paysense_payments.services;

import pk.paysenseapp.paysense_payments.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
