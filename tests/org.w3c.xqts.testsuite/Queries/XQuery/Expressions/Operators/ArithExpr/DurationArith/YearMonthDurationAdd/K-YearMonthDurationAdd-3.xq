(:*******************************************************:)
(: Test: K-YearMonthDurationAdd-3                        :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:36+02:00                       :)
(: Purpose: Simple test of adding two arbitrary xs:yearMonthDurations. :)
(:*******************************************************:)
xs:yearMonthDuration("P32Y43M") + xs:yearMonthDuration("P12Y13M")
		eq xs:yearMonthDuration("P48Y8M")