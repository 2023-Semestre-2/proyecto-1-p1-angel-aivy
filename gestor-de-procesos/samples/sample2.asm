MOV AX, 5
MOV BX, 5
CMP AX, BX
JE Equal
JMP NotEqual
Equal:
JMP EndProgram
NotEqual:
EndProgram:
