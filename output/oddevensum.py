def _init_OddEven(odd, even):
	return { "odd":odd, "even":even }

def odd_even_sum(n, odd, even, i):
	ret = None
	if (i > n):
		ret = _init_OddEven(odd, even)
	else:
		newOdd = None
		newEven = None
		if i % 2.0:
			newOdd = odd + i
			newEven = even
		else:
			newOdd = odd
			newEven = even + i

		ret = odd_even_sum(n, newOdd, newEven, (i + 1.0))

	return ret

oddeven = odd_even_sum(100.0, 0.0, 0.0, 1.0)
print(oddeven["odd"])
print(oddeven["even"])
print(oddeven["odd"] + oddeven["even"])
