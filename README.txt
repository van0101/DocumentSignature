
Create Signature Request

POST Request: http://localhost:8080/file/upload
(using Option 1)
file: 
Multipart File (pdf)

request:
{
"name":"abc",
"id":10
}

Response:

{
    "filename": "<FileName>.pdf",
    "message": "File is uploaded!"
}

--------------------------------------------

Get Document Details API

GET : http://localhost:8080/file/document/DID10ABCD123

Response:

{
    "documentSignature": {
        "name": "abc",
        "id": 10
    },
    "did": "DID10ABCD123",
    "fileName": "MulltimeterUse.pdf",
    "filePath": "doc/",
    "uuidfileName": "f3d4255e-a58e-4e66-beee-d6df45754d70.pdf"
}

------------------------------------------------