(:*******************************************************:)
(: Test: K-SeqExistsFunc-10                              :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: The Dynamic Context property 'current dateTime' must have an explicit timezone when presented as a xs:dateTime. :)
(:*******************************************************:)
exists(timezone-from-dateTime(current-dateTime()))