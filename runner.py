from ctypes import *
from ctypes.wintypes import *
from multiprocessing import Process, Array, Queue

import time
import realTimeDisplay
import ReadWriteMem
import PlayHelper
import array
import AlwaysTowardsBallAgent
import JavaAgent

OpenProcess = windll.kernel32.OpenProcess
CloseHandle = windll.kernel32.CloseHandle

ph = PlayHelper.play_helper()

def updateInputs(inputs, scoring, ph):

	PROCESS_ALL_ACCESS = 0x1F0FFF

	rwm = ReadWriteMem.ReadWriteMem()
	pid = rwm.GetProcessIdByName("RocketLeague.exe")
	rocketLeagueBaseAddress = rwm.GetBaseAddress(pid)

	processHandle = OpenProcess(PROCESS_ALL_ACCESS, False, pid)
	
	blueScore = None
	orangeScore = None
	blueDemo = None
	orangeDemo = None
	
	addresses = ph.GetAddressVector(processHandle,rocketLeagueBaseAddress)
	while(True):
		values = ph.GetValueVector(processHandle, addresses)
		
		# Process scoring to see if any new goals or demos
		if (blueScore == None):
			# Need to update values if don't already exist
			blueScore = values[1][0]
			orangeScore = values[1][1]
			blueDemo = values[1][2]
			orangeDemo = values[1][3]

		if (not blueScore == values[1][0]):
			print("Blue has scored! Waiting for ball and players to reset")
			blueScore = values[1][0]
			time.sleep(15) # Sleep 15 seconds for goal and replay then ping for correct values
			addresses = ph.GetAddressVector(processHandle,rocketLeagueBaseAddress)
			while (ph.ping_refreshed_pointers(processHandle, addresses)):
				time.sleep(0.5)
				addresses = ph.GetAddressVector(processHandle,rocketLeagueBaseAddress)

		if (not orangeScore == values[1][1]):
			print("Orange has scored! Waiting for ball and players to reset")
			orangeScore = values[1][1]
			time.sleep(15) # Sleep 15 seconds for goal and replay then ping for correct values
			addresses = ph.GetAddressVector(processHandle,rocketLeagueBaseAddress)
			while (ph.ping_refreshed_pointers(processHandle, addresses)):
				time.sleep(0.5)
				addresses = ph.GetAddressVector(processHandle,rocketLeagueBaseAddress)

		if (not blueDemo == values[1][2]):
			print("Orange has scored a demo on blue! Waiting for blue player to reset")
			blueDemo = values[1][2]
			time.sleep(4) # Takes about 3 seconds to respawn for a demo
			addresses = ph.GetAddressVector(processHandle,rocketLeagueBaseAddress)

		if (not orangeDemo == values[1][3]):
			print("Blue has scored a demo on orange! Waiting for orange player to reset")
			orangeDemo = values[1][3]
			time.sleep(4) # Takes about 3 seconds to respawn from demo. Even though blue can keep playing, for training I am just sleeping
			addresses = ph.GetAddressVector(processHandle,rocketLeagueBaseAddress)

		# Finally update input to values
		for i in range(len(values[0])):
			inputs[i] = values[0][i]
		for i in range(len(values[1])):
			scoring[i] = values[1][i]
		time.sleep(0.01)

def runAgent(inputs, scoring, agent, q):
	# Deep copy inputs?
	while(True):
		output1 = agent.get_output_vector((inputs,scoring))
		try:
			q.put(output1)
		except Queue.Full:
			pass
		time.sleep(0.01)
			
if __name__ == '__main__':

	time.sleep(3) # Sleep 3 second before starting to give me time to set things up

	inputs = Array('f', [0.0 for x in range(38)])
	scoring = Array('f', [0.0 for x in range(12)])
	agent1 = JavaAgent.agent("blue")
	agent2 = JavaAgent.agent("orange")
	q1 = Queue(1)
	q2 = Queue(1)
	
	output1 = [16383, 16383, 32767, 0, 0, 0, 0]
	output2 = [16383, 16383, 32767, 0, 0, 0, 0]
	
	rtd = realTimeDisplay.real_time_display()
	rtd.build_initial_window(agent1.get_bot_name(), agent2.get_bot_name())
	
	ph = PlayHelper.play_helper()
	
	p1 = Process(target=updateInputs, args=(inputs, scoring, ph))
	p1.start()
	p2 = Process(target=runAgent, args=(inputs, scoring, agent1, q1))
	p2.start()
	p3 = Process(target=runAgent, args=(inputs, scoring, agent2, q2))
	p3.start()
	
	while (True):
		updateFlag = False
		
		rtd.UpdateDisplay((inputs,scoring))
		
		try:
			output1 = q1.get()
			updateFlag = True
		except Queue.Empty:
			pass
			
		try:
			output2 = q2.get()
			updateFlag = True
		except Queue.Empty:
			pass
		
		if (updateFlag):
			ph.update_controllers(output1, output2)
		
		rtd.UpdateKeyPresses(output1, output2)
		time.sleep(0.01)