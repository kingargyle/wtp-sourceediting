(:*******************************************************:)
(: Test: K-ForExprWithout-51                             :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:37+02:00                       :)
(: Purpose: Ensure scanners/parsers accept small QNames in for-expressions(#2). :)
(:*******************************************************:)
1 eq (for $xs:a in 1 return $xs:a)