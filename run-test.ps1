# ===============================
# run-tests.ps1 - versão final
# ===============================
# Define Java 21 apenas para este terminal

$Env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
$Env:PATH = "$Env:JAVA_HOME\bin;C:\tools\apache-maven-3.9.12\bin;$Env:PATH"


# Mostra informações do ambiente

Write-Host "==============================="
Write-Host "Usando Java:"
java -version

Write-Host "==============================="
Write-Host "Usando Maven:"
mvn -v

Write-Host "==============================="
Write-Host "Rodando build e testes..."
mvn clean test

Write-Host "==============================="
Write-Host "Build/testes finalizados."

