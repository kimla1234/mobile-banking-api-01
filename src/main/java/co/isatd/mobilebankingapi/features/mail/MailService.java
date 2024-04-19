package co.isatd.mobilebankingapi.features.mail;

import co.isatd.mobilebankingapi.features.mail.dto.MailRequest;
import co.isatd.mobilebankingapi.features.mail.dto.MailResponse;
import jakarta.mail.MessagingException;

public interface MailService {
    MailResponse send(MailRequest mailRequest);
}
