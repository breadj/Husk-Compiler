def fibHelper(k, a, z):
	x = None
	if k == 0.0:
		x = a
	else:
		print(z)
		x = fibHelper((k - 1.0), z, (a + z))

	return x

def fibonacci(n):
	x = None
	if n <= 1.0:
		x = n
	else:
		x = fibHelper(n, 0.0, 1.0)

	return x

x = fibonacci(12.0)
