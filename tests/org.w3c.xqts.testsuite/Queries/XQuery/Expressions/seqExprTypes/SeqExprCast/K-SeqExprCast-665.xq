(:*******************************************************:)
(: Test: K-SeqExprCast-665                               :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:38+02:00                       :)
(: Purpose: 'castable as' involving xs:duration as source type and xs:dayTimeDuration as target type should always evaluate to true. :)
(:*******************************************************:)
xs:duration("P1Y2M3DT10H30M") castable as xs:dayTimeDuration