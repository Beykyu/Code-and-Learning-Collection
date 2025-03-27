# Security Coursework - Stream Cipher Implementation

This project implements a stream cipher system with various cryptographic operations and analysis tools. The implementation includes encryption, decryption, and cipher text manipulation capabilities.

## Components

### 1. Stream Cipher (cipher.py)
- Implementation of KSA (Key Scheduling Algorithm)
- PRGA (Pseudo-Random Generation Algorithm)
- Encryption and decryption functionality
- Command-line interface for file operations

Usage:
```bash
# For encryption
python cipher.py e key.txt plaintext.txt

# For decryption
python cipher.py d key.txt ciphertext.txt
```

### 2. Cipher Text Manipulation (partB.py)
- Allows modification of specific bytes in encrypted text
- Supports byte replacement with XOR operations
- Maintains cipher text integrity during modifications

### 3. Cryptanalysis Tools (partc.py)
- Implementation of cryptanalysis functions
- Includes GCD calculations and modular inverse operations
- Analysis of cipher text patterns

## File Structure
```
├── cipher.py          # Main stream cipher implementation
├── partB.py          # Cipher text manipulation tools
├── partc.py          # Cryptanalysis functions
├── key.txt           # Secret key storage
├── plaintext.txt     # Original text files
├── ciphertext.txt    # Encrypted output files
└── test files        # Various test files for validation
```

## Input Files
- **plaintext.txt**: Contains the original message to be encrypted
- **key.txt**: Contains the secret key for encryption/decryption
- **ciphertext.txt**: Stores the encrypted output
- **testPartB.txt**: Test file for cipher text manipulation

## Security Features
- Stream cipher implementation using KSA/PRGA
- Secure key handling
- XOR-based encryption
- Support for byte-level manipulation
- Cryptanalysis capabilities

## Requirements
- Python 3.x
- No additional libraries required

## Testing
Test files are provided to verify:
- Encryption/decryption functionality
- Cipher text manipulation
- Key handling
- Error cases
