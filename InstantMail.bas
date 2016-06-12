'Deklarationen + Initialisationen:
$crystal = 16000000
'Quarzfrequenz
$regfile = "m8def.dat"
'Registerdefinitionen Mega8
Config Portd = Output

Dim Analogeingang1 As Word
Dim Analogeingang2 As Word
Dim Analogeingang3 As Word
Dim Analogeingang4 As Word
Dim Act As Byte

Config Adc = Single , Prescaler = Auto , Reference = Avcc


Do
Act = 0
Analogeingang1 = Getadc(0)
Analogeingang2 = Getadc(1)
Analogeingang3 = Getadc(2)
Analogeingang4 = Getadc(3)


If Analogeingang1 < 400 Then
Act = Act + 1
End If

If Analogeingang2 < 400 Then
Act = Act + 1
End If

If Analogeingang3 < 400 Then
Act = Act + 1
End If

If Analogeingang4 < 400 Then
Act = Act + 1
End If


If Act > 0 Then
Portd.5 = 1
Else
Portd.5 = 0
End If

Loop

End