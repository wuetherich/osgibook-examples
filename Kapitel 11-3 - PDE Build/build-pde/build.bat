@echo off
@rem Fuehrt den PDE Build aus und baut das "org.osgibook.feature"-Feature mit allen Bundles

@rem Zeigt auf das Verzeichnis, in dem Eclipse installiert ist
set ECLIPSE_HOME="U:/software/ide/eclipse33/"

@rem Das Workspace-Verzeichnis
set WORKSPACE_HOME="U:/environments/examplemanager-environment/workspace"

@rem Ermitteln des Namens der konkreten launcher-Datei  (inkl. Versionsnummer)
for /f "delims= tokens=1" %%c in ('dir /b %ECLIPSE_HOME%\plugins\org.eclipse.equinox.launcher_*') do set EQUINOXJAR=%ECLIPSEHOME%\plugins\%%c

@rem Ermitteln des Namens des konkrten pde.build-Verzeichnisses (inkl. Versionsnummer)
for /f "delims= tokens=1" %%c in ('dir /b %ECLIPSE_HOME%\plugins\org.eclipse.pde.build_*') do set BUILDPLUGIN=%ECLIPSEHOME%\plugins\%%c

java -jar %ECLIPSE_HOME%\%EQUINOXJAR% -application org.eclipse.ant.core.antRunner -f %ECLIPSE_HOME%\%BUILDPLUGIN%\scripts\build.xml -Dbuilder=%WORKSPACE_HOME%\build-pde\scripts"
