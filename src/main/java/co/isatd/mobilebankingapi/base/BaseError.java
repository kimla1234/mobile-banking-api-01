package co.isatd.mobilebankingapi.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseError<T> {
    private String code;
    private String description;
}
