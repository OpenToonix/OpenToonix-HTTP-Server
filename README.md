# OpenToonix

<div align="center">

[![Discord Server](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/8ZWkyXnv4h)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/OpenToonix)

</div>

HTTP (Hypertext Transfer Protocol) server for Toonix World _(mostly known as **Mundo Toonix**)_.

## Requirements

- **Java -** Version 11
- **Database engine -** MySQL version 8.x
- **Environment variables**

| Variable                      | Description                                               | Example                             |
|-------------------------------|-----------------------------------------------------------|-------------------------------------|
| `ACCOUNTS_FROM_EMAIL_ADDRESS` | The email address that will send account-related emails   | `OpenToonix@gmail.com`              |
| `AVATAR_STORAGE_FOLDER_PATH`  | The path where user avatars will be stored                | `C:/Users/usr/Documents/OpenToonix` |
| `DATABASE_HOST`               | The host where the database server is running             | `localhost`                         |
| `DATABASE_NAME`               | The name of the database                                  | `opentoonix_database`               |
| `DATABASE_PASSWORD`           | The password of the user who owns the database            | `openT00nix_Database_paSsW0rd`      |
| `DATABASE_PORT`               | The port where the database server is running             | `3306`                              |
| `DATABASE_USERNAME`           | The name of the user who owns the database                | `opentoonix_user`                   |
| `JWT_SECRET`                  | The secret used to sign and verify JSON Web Tokens        | `jWT_S3cR3T`                        |
| `KEYSTORE_PATH`              | The path where keystore for ssl is stored          | `C:/Users/usr/keystore/key.p12`     |
| `KEYSTORE_PASSWORD`          | The password for the keystore                      | `keySt0re`                          |
| `KEYSTORE_TYPE`              | The certificate format from the keystore           | `PKCS12`                            |
| `MAIL_HOST`                   | The host of the mail server                               | `smtp.gmail.com`                    |
| `MAIL_PORT`                   | The mail port for SMTP                                    | `587`                               |
| `MAIL_USERNAME`               | The username used to authenticate against the SMTP server | `apikey`                            |
| `MAIL_PASSWORD`               | The password of the mail account                          | `openT00nix_Mail_paSsW0rd`          |
| `MYSQL_ROOT_PASSWORD`         | The password of the database root user                    | `openT00nix_Database_paSsW0rd`      |
 <br>

   **Notes:**
   - All environment variables must be set before running the application.
   - The database server must support `utf8` charset with `utf8_unicode_ci` collation.
   - Never give environment variable values to non-administrator users, since they could hack your server instance.

## Features

You can check the current state of features [here](https://github.com/OpenToonix/OpenToonix-HTTP-Server/wiki/Features).

## Support

For further support, you can join us on [Discord](https://discord.gg/8ZWkyXnv4h).
