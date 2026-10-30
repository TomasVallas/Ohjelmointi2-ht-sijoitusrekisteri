@echo off
rem Kaynnistaa Sijoitusrekisterin. Ajetaan aina taman tiedoston kansiosta,
rem koska ohjelma lukee datan polusta sijoitusrekisteri\*.dat.
cd /d "%~dp0"
java --enable-preview -jar sijoitusrekisteri2.jar
if errorlevel 1 pause
