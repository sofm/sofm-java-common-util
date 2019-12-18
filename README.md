# Sofm/ Java Common Util
A simple library that provides utility handling functions.

## Code  style
Google Java Format is a program that reformats Java source code to comply with [Google Java Style.](https://google.github.io/styleguide/javaguide.html "Google Java Style.")

Here you can download and install the java codestyle: [https://github.com/google/google-java-format](https://github.com/google/google-java-format "https://github.com/google/google-java-format")

## Usage
#### Dependencies
- JDK >= 1.8
- Maven >= 3.0

####  How to package
`mvn clean package -Dmaven.test.skip=true`

#### How to install in local maven repository
`mvn clean install -Dmaven.test.skip=true`

#### Using Lib with Maven
    <dependency>
      <groupId>com.github.sofm</groupId>
      <artifactId>sofm-java-common-util</artifactId>
      <version>1.0-RELEASE</version>
    </dependency>

## License
This library is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0 "Apache License").

    Copyright 2019 Sofm

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

               http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
