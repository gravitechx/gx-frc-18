import threading
import socket
info_lock=threading.Lock()
offset=0
distance=10
def comms():
	sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
	sock.connect(("127.0.0.1",5800))
	while True:
		with info_lock:
			sock.send('{"OFFSET":"' + offset + '","DISTANCE":"' + distance + '","TIME":"' + str(time.time()-start) + '}')
def vision():
	
start=time.time()
t1=threading.Thread(target=comms)
t2=threading.Thread(target=vision)
t1.daemon=True
t2.daemon=True
t1.start()
t2.start()
#t1.join()
#t2.join()
#cubic(array)
#square(array)
#print("All done in " + str(time.time()-start) + " seconds!")
