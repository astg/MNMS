
import RPi.GPIO as GPIO
from time import sleep
import pyodbc
import socket
import fcntl
import struct

GPIO.setmode(GPIO.BCM)
GPIO.setup(2, GPIO.IN)

beforeState = GPIO.input(2)
currentState = beforeState
lowState = 0
highState = 1

RASPBERRY_ID = "RASPBERRY_001"

SAAT_00_01 ="[00:00-01:00]"
SAAT_01_02 ="[01:00-02:00]"
SAAT_02_03 ="[02:00-03:00]"
SAAT_03_04 ="[03:00-04:00]"
SAAT_04_05 ="[04:00-05:00]"
SAAT_05_06 ="[05:00-06:00]"
SAAT_06_07 ="[06:00-07:00]"
SAAT_07_08 ="[07:00-08:00]"
SAAT_08_09 ="[08:00-09:00]"
SAAT_09_10 ="[09:00-10:00]"
SAAT_10_11 ="[10:00-11:00]"
SAAT_10_11 ="[10:00-11:00]"
SAAT_11_12 ="[11:00-12:00]"
SAAT_12_13 ="[12:00-13:00]"
SAAT_13_14 ="[13:00-14:00]"
SAAT_14_15 ="[14:00-15:00]"
SAAT_15_16 ="[15:00-16:00]"
SAAT_16_17 ="[16:00-17:00]"
SAAT_17_18 ="[17:00-18:00]"
SAAT_18_19 ="[18:00-19:00]"
SAAT_19_20 ="[19:00-20:00]"
SAAT_20_21 ="[20:00-21:00]"
SAAT_21_22 ="[21:00-22:00]"
SAAT_22_23 ="[22:00-23:00]"
SAAT_23_00 ="[23:00-00:00]"
SAAT_00_01 ="[00:00-01:00]"
SAAT_01_02 ="[01:00-02:00]"
SAAT_02_03 ="[02:00-03:00]"
SAAT_03_04 ="[03:00-04:00]"
SAAT_04_05 ="[04:00-05:00]"
SAAT_05_06 ="[05:00-06:00]"
SAAT_06_07 ="[06:00-07:00]"
SAAT_07_08 ="[07:00-08:00]"


conn = pyodbc.connect('DRIVER=FreeTDS;SERVER=10.3.0.107;PORT=1433;DATABASE=SMARTPHONE_TEST;UID=astg;PWD=asd.1234;TDS_Version=4.2')
cursor=conn.cursor()

row = cursor.execute('SELECT TOP 1 Makine_Id, Bolum_Id FROM [SMARTPHONE_TEST].[dbo].[Raspberry_Makina_Id_Eslestirme] Where  Raspberry_Id = "'+RASPBERRY_ID+'"').fetchone()
Makine_Id = row[0]
Bolum_Id = row[1]

def get_ip_address(ifname):
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    return socket.inet_ntoa(fcntl.ioctl(
        s.fileno(),
        0x8915,  # SIOCGIFADDR
        struct.pack('256s', ifname[:15])
    )[20:24])
cursor.execute('UPDATE [SMARTPHONE_TEST].[dbo].[Raspberry_Makina_Id_Eslestirme] SET Ip_Adresi ="'+get_ip_address('eth0')+'" Where Raspberry_Id = "'+RASPBERRY_ID+'"')
conn.commit()



def tarihe_gore_sayilari_guncelle():
    dateTimeFromSQL = cursor.execute('SELECT GETDATE()').fetchone()
    tarih = dateTimeFromSQL[0]
    tarih = tarih.strftime('%Y-%m-%d')
    guncelSaat = dateTimeFromSQL[0].hour
    if(guncelSaat < 8):
        dateTimeFromSQL = cursor.execute('SELECT GETDATE() - 1').fetchone()
        tarih = dateTimeFromSQL[0]
        tarih = tarih.strftime('%Y-%m-%d')
    
    row = cursor.execute('SELECT TOP 1 Tarih FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Net] Where Tarih like "%'+tarih+'%" and Makine_Id = "'+Makine_Id+'" and Bolum_Id = "'+Bolum_Id+'"').fetchone()
    if row is None:
        cursor.execute('INSERT INTO Industry_4_0_Makina_Durus_Kayitlari_Net(Bolum_Id, Makine_Id,Tarih,[08:00-09:00],[09:00-10:00],[10:00-11:00],[11:00-12:00],[12:00-13:00],[13:00-14:00],[14:00-15:00],[15:00-16:00],[16:00-17:00],[17:00-18:00],[18:00-19:00],[19:00-20:00],[20:00-21:00],[21:00-22:00],[22:00-23:00],[23:00-00:00],[00:00-01:00],[01:00-02:00],[02:00-03:00],[03:00-04:00],[04:00-05:00],[05:00-06:00],[06:00-07:00],[07:00-08:00]) VALUES ("'+Bolum_Id+'","'+Makine_Id+'","'+tarih+'","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0")')
        conn.commit()
    

    if(guncelSaat == 0):
        sayiyi_arttir(SAAT_00_01,tarih)
    elif(guncelSaat==1):
        sayiyi_arttir(SAAT_01_02,tarih)
    elif(guncelSaat==2):
        sayiyi_arttir(SAAT_02_03,tarih)
    elif(guncelSaat==3):
        sayiyi_arttir(SAAT_03_04,tarih)
    elif(guncelSaat==4):
        sayiyi_arttir(SAAT_04_05,tarih)
    elif(guncelSaat==5):
        sayiyi_arttir(SAAT_05_06,tarih)
    elif(guncelSaat==6):
        sayiyi_arttir(SAAT_06_07,tarih)
    elif(guncelSaat==7):
        sayiyi_arttir(SAAT_07_08,tarih)
    elif(guncelSaat==8):
        sayiyi_arttir(SAAT_08_09,tarih)
    elif(guncelSaat==9):
        sayiyi_arttir(SAAT_09_10,tarih)
    elif(guncelSaat==10):
        sayiyi_arttir(SAAT_10_11,tarih)
    elif(guncelSaat==11):
        sayiyi_arttir(SAAT_11_12,tarih)
    elif(guncelSaat==12):
        sayiyi_arttir(SAAT_12_13,tarih)
    elif(guncelSaat==13):
        sayiyi_arttir(SAAT_13_14,tarih)
    elif(guncelSaat==14):
        sayiyi_arttir(SAAT_14_15,tarih)
    elif(guncelSaat==15):
        sayiyi_arttir(SAAT_15_16,tarih)
    elif(guncelSaat==16):
        sayiyi_arttir(SAAT_16_17,tarih)
    elif(guncelSaat==17):
        sayiyi_arttir(SAAT_17_18,tarih)
    elif(guncelSaat==18):
        sayiyi_arttir(SAAT_18_19,tarih)
    elif(guncelSaat==19):
        sayiyi_arttir(SAAT_19_20,tarih)
    elif(guncelSaat==20):
        sayiyi_arttir(SAAT_20_21,tarih)
    elif(guncelSaat==21):
        sayiyi_arttir(SAAT_21_22,tarih)
    elif(guncelSaat==22):
        sayiyi_arttir(SAAT_22_23,tarih)
    elif(guncelSaat==23):
        sayiyi_arttir(SAAT_23_00,tarih)
    else:
        print ""
    
    return tarih
    
def sayiyi_arttir(saat,guncelTarih):

    row = cursor.execute('SELECT TOP 1 '+saat+' , Total FROM [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Net] Where Bolum_Id = "'+Bolum_Id+'" and Makine_Id = "'+Makine_Id+'" and Tarih = "'+guncelTarih+'"').fetchone()
    count = int(row[0])
    totalCount = int(row[1])
    count = count +1
    print count
    print totalCount
    cursor.execute('UPDATE [SMARTPHONE_TEST].[dbo].[Industry_4_0_Makina_Durus_Kayitlari_Net] SET '+saat+' = "'+str(count)+'" where Bolum_Id = "'+Bolum_Id+'" and Makine_Id = "'+Makine_Id+'" and Tarih = "'+guncelTarih+'"')
    conn.commit()


sayac = 0 
highKalmaSayaci = 0
makine_adi = "Onur"



try:    
    while (True):
        
        beforeState = currentState
        currentState = GPIO.input(2)
        #print(GPIO.input(2))
        if(beforeState != highState & currentState == highState):
            while(GPIO.input(2) == 1):
                sleep(0.2)
                highKalmaSayaci = highKalmaSayaci + 1
                if(highKalmaSayaci==20):
                    print "+1"
                    sleep(0.5)
                    tarihe_gore_sayilari_guncelle()
            highKalmaSayaci = 0
        highKalmaSayaci =0

except KeyboardInterrupt:
    GPIO.cleanup()
    print("ahmet")

