(:*******************************************************:)
(: Test: K-SeqExprCast-615                               :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:38+02:00                       :)
(: Purpose: 'castable as' involving xs:integer as source type and xs:dateTime as target type should always evaluate to false. :)
(:*******************************************************:)
not(xs:integer("6789") castable as xs:dateTime)