package co.isatd.mobilebankingapi.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseErrorResponse {
    private BaseError baseError;
}