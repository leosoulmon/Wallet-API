@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.2.0
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_SCRIPTDIR=%~dp0

@SETLOCAL

@SET __MVNW_PROPS_FILE__=%__MVNW_SCRIPTDIR%.mvn\wrapper\maven-wrapper.properties

@FOR /F "usebackq tokens=1,* delims==" %%A IN ("%__MVNW_PROPS_FILE__%") DO (
  @IF "%%A"=="distributionUrl" SET __MVNW_DIST_URL__=%%B
)

@SET __MVNW_DIST_VERSION__=%__MVNW_DIST_URL__:*apache-maven-=%
@SET __MVNW_DIST_VERSION__=%__MVNW_DIST_VERSION__:-bin.zip=%

@SET __MVNW_MAVEN_HOME__=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%__MVNW_DIST_VERSION__%

@IF EXIST "%__MVNW_MAVEN_HOME__%\bin\mvn.cmd" GOTO :mvnw_exec

@ECHO Downloading Apache Maven %__MVNW_DIST_VERSION__% to %__MVNW_MAVEN_HOME__%
@ECHO Distribution URL: %__MVNW_DIST_URL__%

@IF NOT EXIST "%__MVNW_MAVEN_HOME__%" MKDIR "%__MVNW_MAVEN_HOME__%"

@SET __MVNW_ZIP__=%__MVNW_MAVEN_HOME__%\maven-dist.zip

@powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object System.Net.WebClient).DownloadFile('%__MVNW_DIST_URL__%', '%__MVNW_ZIP__%')"
@IF NOT EXIST "%__MVNW_ZIP__%" (
  ECHO ERROR: Failed to download Maven. Check your internet connection.
  EXIT /B 1
)

@powershell -Command "Add-Type -AssemblyName System.IO.Compression.FileSystem; [System.IO.Compression.ZipFile]::ExtractToDirectory('%__MVNW_ZIP__%', '%__MVNW_MAVEN_HOME__%')"
@DEL "%__MVNW_ZIP__%"

@FOR /D %%D IN ("%__MVNW_MAVEN_HOME__%\apache-maven-*") DO (
  XCOPY /E /I /Q "%%D\*" "%__MVNW_MAVEN_HOME__%\" >NUL
  RD /S /Q "%%D"
)

:mvnw_exec
@SET MAVEN_CMD="%__MVNW_MAVEN_HOME__%\bin\mvn.cmd"
@%MAVEN_CMD% %MAVEN_CONFIG% %*
@SET __MVNW_EXIT__=%ERRORLEVEL%
@ENDLOCAL
@EXIT /B %__MVNW_EXIT__%
