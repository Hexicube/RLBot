import pyvjoy
import time

p2 = pyvjoy.VJoyDevice(2)

time.sleep(2)

p2.data.lButtons = 128
p2.update()
time.sleep(0.25)
p2.data.lButtons = 1
p2.update()

time.sleep(1)

p2.data.lButtons = 0
p2.update()