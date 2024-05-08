def factorial(n):
	x = None
	if n <= 1.0:
		x = n
	else:
		x = n * factorial((n - 1.0))

	return x

fac = factorial(9.0 - 1.0)
print(fac)
