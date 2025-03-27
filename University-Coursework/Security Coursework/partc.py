from fractions import gcd

if __name__ == '__main__':
    import sys

    file = open(sys.argv[1], 'r')
    cipherA = file.read()
    file.close()

    file = open(sys.argv[2], 'r')
    cipherB = file.read()
    file.close()

    file = open(sys.argv[3], 'r')
    plainA = file.read()
    file.close()


    def convert(s):
        return [ord(c) for c in s]

    plain = convert(plainA)

    n = 0
    cipherAUni = []
    while n < len(cipherA):
        x = int(cipherA[n:n+2], 16)
        cipherAUni.append(x)
        n += 2

    print(cipherA)

    cipherAval0 = cipherAUni[0]
    cipherAval1 = cipherAUni[1]
    cipherAval2 = cipherAUni[2]
    plainAval0 = plain[0]
    plainAval1 = plain[1]
    plainAval2 = plain[2]

    print(cipherAval0)
    print(plainAval0)
    print(plainAval1)
    print(plainAval2)
    A = (cipherAval1 - cipherAval2) % 256
    print(A)
    B = (cipherAval0 - cipherAval1) % 256
    print(B)

    check = (cipherAval1 - cipherAval2) % 256
    alpha = (plainAval1 - plainAval2) % 256


    
    def egcd(a, b):
        if a == 0:
            return (b, 0, 1)
        else:
            g, y, x = egcd(b % a, a)
            return (g, x - (b // a) * y, y)

    def modinv(a, m):
        g, x, y = egcd(a, m)
        if g != 1:
            raise Exception('modular inverse does not exist')
        else:
            return x % m

