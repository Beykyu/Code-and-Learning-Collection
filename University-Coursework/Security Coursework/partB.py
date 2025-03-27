if __name__ == '__main__':
    import sys

    def convert(s):
        return [ord(c) for c in s]

    file = open(sys.argv[1], 'r')
    cipherA = file.read()
    file.close()

    k1 = int(sys.argv[2])
    k2 = int(sys.argv[3])
    org = sys.argv[4]
    rep = sys.argv[5]

    #Start of the byte
    kk1 = ((k1 - 1) * 2)
    #End byte
    kk2 = (k2 * 2)

    
    #Get the bytes to change
    x = kk1
    k3 = ""
    while x < kk2 :
       k3 += cipherA[x]
       x += 1
    

    #Get the ciphertext integers of the cipher text
    n = 0
    cB = []
    while n < len(k3):
        x = int(k3[n:n+2], 16)
        cB.append(x)
        n += 2
    print(cB)

    #Get the ciphertext integers of the original and replacement
    org = convert(org)
    rep = convert(rep)
    
    #XOR the ciphertext integers of the orginal and replacement
    replace = []
    x = 0
    for c in org:
        replace.append(c ^ rep[x])
        x +=1

    #XOR the ciphertext integers of the current bytes with what was given in the previous XOR 
    replacement = []
    x = 0
    for c in replace:
        replacement.append(c ^ cB[x])
        x +=1
    
    #Convert the XOR'd ciphertext integers back into hex 
    x = 0
    for c in replacement:
        replacement[x] = "%02X" %(replacement[x])
        x +=1
    
    stringRep = ''.join(replacement)
    print(k3)
    print(stringRep)
    newcipher =  cipherA.replace(k3, stringRep)

    file = open(sys.argv[1], 'w')
    file.seek(0)
    file.write(newcipher)
    file.close
    
