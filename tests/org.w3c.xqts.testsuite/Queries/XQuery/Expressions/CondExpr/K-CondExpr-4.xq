(:*******************************************************:)
(: Test: K-CondExpr-4                                    :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:37+02:00                       :)
(: Purpose: An expression involving the if expression that trigger certain optimization paths in some implementations(#2). :)
(:*******************************************************:)
(if(boolean((1, current-time())[1] treat as xs:integer)) then true() else false()) eq true()