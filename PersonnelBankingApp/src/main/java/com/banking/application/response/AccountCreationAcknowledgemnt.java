package com.banking.application.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationAcknowledgemnt  implements Serializable
{
   private static final long serialVersionUID = -4124940610423832188L;
   private String StatusMsg;
}
