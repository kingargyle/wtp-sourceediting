(:*******************************************************:)
(:Test: op-hexBinary-equal2args-2                         :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:18 GMT-05:00 2004                :)
(:Purpose: Evaluates The "op:hexBinary-equal" operator   :)
(: with the arguments set as follows:                    :)
(:$value1 = xs:hexBinary(mid range)                      :)
(:$value2 = xs:hexBinary(lower bound)                    :)
(:*******************************************************:)

xs:hexBinary("6e7875626264756366") eq xs:hexBinary("767479716c6a647663")