(:*******************************************************:)
(: Test: K-SeqExprCast-792                               :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:38+02:00                       :)
(: Purpose: Casting from xs:dayTimeDuration to xs:anyURI isn't allowed. :)
(:*******************************************************:)
xs:dayTimeDuration("P3DT2H") cast as xs:anyURI