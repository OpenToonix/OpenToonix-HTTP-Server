# OpenToonix HTTP Server

<div style="text-align: center">

[![Discord Server](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/8ZWkyXnv4h)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/OpenToonix)

</div>

HTTP (Hypertext Transfer Protocol) server
for Toonix World _(mostly known as **Mundo Toonix**)_.

## Requirements

- **Java -** Version 17
- **Database engine -** MySQL version 8.x

    Before installing the database engine,
    you should verify the compatibility of the database engine
    with Flyway, the database migration tool used in this project.

    You can check the compatibility of the database engine
    with Flyway [here](https://documentation.red-gate.com/flyway/flyway-cli-and-api/supported-databases/mysql).

- **Environment variables**

    | Variable                                         | Type    | Description                                                                               | Required | Default                 | Example                                                |
    |--------------------------------------------------|---------|-------------------------------------------------------------------------------------------|----------|-------------------------|--------------------------------------------------------|
    | `AVATAR_STORAGE_FOLDER_PATH`                     | String  | The path where user avatars will be stored                                                | Yes      | None                    | `C:/Users/usr/Documents/OpenToonix/avatars`            |
    | `CORS_ALLOWED_ORIGINS`                           | String  | The allowed origins for CORS requests, separated by `,`                                   | No       | `http://localhost:80`   | `https://amf-services.example.com,https://example.com` |
    | `DATABASE_HOST`                                  | String  | The host where the database server is running                                             | Yes      | None                    | `localhost`                                            |
    | `DATABASE_NAME`                                  | String  | The name of the database                                                                  | Yes      | None                    | `<MySQL database name>`                                |
    | `DATABASE_PASSWORD`                              | String  | The password of the user who owns the database                                            | Yes      | None                    | `<MySQL user password>`                                |
    | `DATABASE_PORT`                                  | Integer | The port where the database server is running                                             | Yes      | None                    | `3306`                                                 |
    | `DATABASE_USERNAME`                              | String  | The name of the user who owns the database                                                | Yes      | None                    | `<MySQL user>`                                         |
    | `ISSUER_ACCOUNT_RELATED_NAME`                    | String  | The name of the issuer of account-related emails                                          | No       | `${ISSUER_NAME}`        | `OpenToonix Accounts`                                  |
    | `ISSUER_NAME`                                    | String  | The name of the issuer of the emails                                                      | Yes      | None                    | `OpenToonix`                                           |
    | `ISSUER_SUPPORT_ADDRESS`                         | String  | The address used to give support to users                                                 | Yes      | None                    | `support@example.com`                                  |
    | `JWT_SECRET`                                     | String  | The secret used to sign and verify JSON Web Tokens                                        | Yes      | None                    | `<JSON Web Token secret>`                              |
    | `MAIL_SERVER_FROM_ACCOUNT_RELATED_EMAIL_ADDRESS` | String  | The email address used to send emails related to account management                       | Yes      | None                    | `example@example.com`                                  |
    | `MAIL_SERVER_HOST`                               | String  | The host where the email server is running on                                             | No       | `smtp.gmail.com`        | `smtp.example.com`                                     |
    | `MAIL_SERVER_PASSWORD`                           | String  | The password of the email account used to send emails                                     | Yes      | None                    | `<Mail server password>`                               |
    | `MAIL_SERVER_PORT`                               | Integer | The port where the email server is running on                                             | No       | `587`                   | `465`                                                  |
    | `MAIL_SERVER_USERNAME`                           | String  | The username of the email account used to send emails                                     | Yes      | None                    | `<Mail server username>`                               |
    | `PORT`                                           | Integer | The port where the server will be running                                                 | No       | `8080`                  | `8443`                                                 |
    | `PUBLIC_ADDRESS`                                 | String  | The public address where clients and browsers will be able to access the application from | No       | `http://localhost:8080` | `https://example.com`                                  |
    | `VERIFICATION_TOKENS_SALT_KEY`                   | String  | The salt key used to encrypt and decrypt verification tokens                              | Yes      | None                    | `<Verification token salt key>`                        |
    | `VERIFICATION_TOKENS_SECURITY_KEY`               | String  | The security key used to encrypt and decrypt verification tokens                          | Yes      | None                    | `<Verification token security key>`                    |

    <br>

    **Notes:**
    - All required environment variables must be set before running the application.
    - Never give environment variable values to non-administrator users,
      since they could hack your server instance.
    - You can generate secrets using tools
      like [Random Key Gen](https://randomkeygen.com/).
      256-bit keys are recommended.
    - For setting the `PUBLIC_ADDRESS` environment variable,
      you can use your domain name or your public IP address.
    - For security reasons,
      `VERIFICATION_TOKENS_SALT_KEY` and `VERIFICATION_TOKENS_SECURITY_KEY`
      must be different from each other.
    - For setting up the `MAIL_SERVER_*` environment variables,
      check out the SMTP Server setup section.

### Development Tools

- [EditorConfig](https://editorconfig.org/)
- [Maven](https://maven.apache.org/)

## Configuration

### SSL Configuration

To enable SSL, you can use any of the following methods:

- [Java KeyStore file](https://docs.spring.io/spring-boot/how-to/webserver.html#howto.webserver.configure-ssl).
- [PEM files](https://docs.spring.io/spring-boot/how-to/webserver.html#howto.webserver.configure-ssl.pem-files).
- [SSL Bundle](https://docs.spring.io/spring-boot/reference/features/ssl.html)

Once you have chosen a method to enable SSL,
you can pass the necessary JVM arguments to the application
by passing each argument following the `-D` flag when running the application.

Note that you can also use a reverse proxy (e.g. Nginx, Apache, etc.).

## Features

You can check the current state of features [here](https://github.com/OpenToonix/OpenToonix-HTTP-Server/wiki/Features).

## API Documentation

The API documentation is available at the endpoint `/api/docs`.

## Running

Before running the application,
make sure the option `secure_file_priv`
is set to an empty value in the MySQL configuration file,
which is usually located at `/etc/my.cnf` in Linux systems.
because some database migrations (or database initialization scripts)
require the use of the `LOAD DATA LOCAL INFILE` statement.

For further information about the `secure_file_priv` option,
you can check the [MySQL documentation](https://dev.mysql.com/doc/refman/8.0/en/server-system-variables.html#sysvar_secure_file_priv).

Also, make sure to enable the `local_infile` option in the MySQL configuration file,
or using the following command:

```sql
SET GLOBAL local_infile = 1;
```

For further information about the `local_infile` option,
you can check the [MySQL documentation](https://dev.mysql.com/doc/refman/8.4/en/server-system-variables.html#sysvar_local_infile).

### Using Docker (Recommended)

In order to run the application using [Docker](https://www.docker.com/),
you must have Docker installed on your machine.

For running the application with Docker, you will need to mount
avatars storage folder using Docker volumes
and map port `8080` to your host machine.

#### Docker CLI

- **Windows**

    ```shell
    > docker run -dp 8080:8080 \
        -v <local\path\to\avatars\storage\folder>:${AVATAR_STORAGE_FOLDER_PATH} \
        --env-file <path\to\env\file> \
        --name <container-name> \
        juansecu/opentoonix-http-server:v<version number>
    ```

- **MacOS/Linux**

    ```shell
    $ docker run -dp 8080:8080 \
        -v <local/path/to/avatars/storage/folder>:${AVATAR_STORAGE_FOLDER_PATH} \
        --env-file <path/to/env/file> \
        --name <container-name> \
        juansecu/opentoonix-http-server:v<version number>
    ```

#### Docker Compose

For running the application with Docker Compose, you can use
the [Docker Compose file](https://github.com/OpenToonix/OpenToonix-HTTP-Server/blob/main/docker-compose.yml)
provided in this repository for development and production,
but for production, you will also need to
remove the `build` property from the `http-server` service,
change the `image` property to `juansecu/opentoonix-http-server:v<version number>`
and configure the following environment variables:

- `LOCAL_AVATAR_STORAGE_FOLDER_PATH` - The path in the host machine to the avatars storage folder

You should also add a volume for MySQL data directory (`/var/lib/mysql`)
in the `docker-compose.yml` file

After configuring the environment variables,
you can run the application using the following command:

- **Docker Compose v1**

```shell
$ docker-compose up -d
```

- **Docker Compose v2**

```shell
$ docker compose up -d
```

### Using Java

For running the application with Java,
you will need to set the necessary environment variables
and follow the instructions below.

#### For Development

In order to run the application for development,
you will need to clone this repository,
compile the application and run it using the following commands:

- **Windows**

    ```shell
    # --- BUILDING ---

    # For building the application without running tests
    > .\mvnw package -DskipTests

    # For building the application and running tests (not working yet)
    > .\mvnw package


    # --- RUNNING ---

    # For running the application
    > java -jar .\target\opentoonix-http-server-<version number>.jar
    ```
  
- **MacOS/Linux**

    ```shell
    # --- BUILDING ---

    # For building the application without running tests
    $ ./mvnw package -DskipTests

    # For building the application and running tests (not working yet)
    $ ./mvnw package


    # --- RUNNING ---

    # For running the application
    $ java -jar ./target/opentoonix-http-server-<version number>.jar
    ```

#### For Production

In order to run the application for production,
you will only need to download the latest release
from the [releases page](https://github.com/OpenToonix/OpenToonix-HTTP-Server/releases),
set the necessary environment variables
and run the application using the following command:

- **Windows**

    ```shell
    > java -jar .\opentoonix-http-server-<version number>.jar
    ```

- **MacOS/Linux**

    ```shell
    $ java -jar ./opentoonix-http-server-<version number>.jar
    ```

## Support

For further support, you can join us on [Discord](https://discord.gg/8ZWkyXnv4h).
