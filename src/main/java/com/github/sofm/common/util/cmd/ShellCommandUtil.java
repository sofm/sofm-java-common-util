package com.github.sofm.common.util.cmd;

import com.github.sofm.common.util.ExceptionUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class ShellCommandUtil {

  public static CommandResult runCommand(String command) {
    CommandResult commandResult = new CommandResult();
    StringBuilder stdOut = new StringBuilder();
    StringBuilder stdErr = new StringBuilder();

    try {
      Process process = Runtime.getRuntime().exec(command);
      InputStream cmdStdOut = process.getInputStream();
      InputStream cmdStdErr = process.getErrorStream();
      BufferedReader bufferStdOut = new BufferedReader(new InputStreamReader(cmdStdOut));

      String line;
      while ((line = bufferStdOut.readLine()) != null) {
        stdOut.append(line);
      }

      cmdStdOut.close();
      BufferedReader bufferStdErr = new BufferedReader(new InputStreamReader(cmdStdErr));

      while ((line = bufferStdErr.readLine()) != null) {
        stdErr.append(line);
      }

      cmdStdErr.close();
      commandResult.setExitStatus(process.waitFor());
      commandResult.setOutput(stdOut + "|" + stdErr);
    } catch (Exception ex) {
      log.error("(runCommand)ex: {}", ExceptionUtil.getFullStackTrace(ex));
    }

    return commandResult;
  }

  public static String executeTimeoutCommand(String command, long timeout, TimeUnit timeUnit) {
    StringBuffer output = new StringBuffer();

    try {
      Process process = Runtime.getRuntime().exec(command);
      if (!process.waitFor(timeout, timeUnit)) {
        process.destroyForcibly();
        return Integer.toString(process.exitValue());
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }
    } catch (Exception ex) {
      log.error("(executeTimeoutCommand)timeout: {}, ex:  {}", timeout, ExceptionUtil.getFullStackTrace(ex));
    }

    return output.toString();
  }

  public static String executeCommand(String... command) {
    StringBuffer output = new StringBuffer();

    try {
      ProcessBuilder processBuilder = new ProcessBuilder(command);
      processBuilder.redirectErrorStream(true);
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }

      reader.close();
    } catch (Exception ex) {
      log.error("(executeCommand)ex: {}", ExceptionUtil.getFullStackTrace(ex));
    }

    return output.toString();
  }

  public static String executeCommand(String command) {
    StringBuffer output = new StringBuffer();

    try {
      Process process = Runtime.getRuntime().exec(command);
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }

      reader.close();
    } catch (Exception ex) {
      log.error("(executeCommand)ex: {}", ExceptionUtil.getFullStackTrace(ex));
    }

    return output.toString();
  }
}
