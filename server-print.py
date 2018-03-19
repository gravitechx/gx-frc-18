import socket
sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
sock.bind(("127.0.0.1",5800))
sock.listen(1)
(client,(ip,port))=sock.accept()
while True:
	print(client.recv(4096) + "\n")
