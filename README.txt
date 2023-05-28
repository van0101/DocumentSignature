

Authorization Tab(Basic Auth):
Client Id: user
Client Password: user
-----------------------------------------
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
// The request param as stated can further contain the nuances of a signature.

Response on successful file upload:

Status : File is uploaded!
File Recevied : MulltimeterUse.pdf

// File storage loaction is local(see properties)
--------------------------------------------

Get Document Details API

GET : http://localhost:8080/file/document/DID10ABCD123

Response:

{
    "did": "DID10ABCD123",
    "documentSignature": {
        "name": "abc",
        "id": 10
    },
    "fileName": "MulltimeterUse.pdf",
    "filePath": "doc/",
    "uuidfileName": "fa97512c-20f1-4277-9f97-812eb9176198.pdf"
}
// The response can contain other relevant details for the document implemented in DocumentImpl.java

------------------------------------------------

GET Download Document API

GET : http://localhost:8080/file/document/download?id=DID10ABCD123

Response:

(Postman) Response:
Key: Content-Disposition
Value: <Filename>.pdf

(Browser) Response:
File is downloaded.