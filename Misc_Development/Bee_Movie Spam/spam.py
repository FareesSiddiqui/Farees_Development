import time
from ipaddress import IPv4Address
from pyairmore.request import AirmoreSession
from pyairmore.services.messaging import MessagingService
from random import randint

ip = IPv4Address("192.168.2.163")

session = AirmoreSession(ip)
was_accepted = session.request_authorization()

print("is server running: " , session.is_server_running)
print("Authorized: " , was_accepted)

service = MessagingService(session)

spam_number = "6475423272"
line = 1
f = open("bee_Movie_Script.txt", "r")
for x in f:
    service.send_message(spam_number, x)
    print(x)

    line+=1;

    if(line % 10 == 0):
        time.sleep(randint(10, 15))
    else:
        time.sleep(2) #sleep 3 seconds
