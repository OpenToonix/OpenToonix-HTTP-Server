# OpenToonix

<div align="center">

[![Discord Server](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/8ZWkyXnv4h)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/OpenToonix)

</div>

Open-source web server for Toonix World _(mostly known as **Mundo Toonix**)_.

## Requirements

- **Java** - Version 11

- **Database engine** - MySQL version 8.x

- **Environment variables**

    | Variable                     | Description                                        | Default | Example                             | Required |
    | ---------------------------- | -------------------------------------------------- | ------- | ----------------------------------- | -------- |
    | `AVATAR_STORAGE_FOLDER_PATH` | The path where user avatars will be stored         | None    | `C:/Users/usr/Documents/OpenToonix` | Yes      |
    | `DATABASE_HOST`              | The host where the database server is running      | None    | `localhost`                         | Yes      |
    | `DATABASE_NAME`              | The name of the database                           | None    | `opentoonix_database`               | Yes      |
    | `DATABASE_PASSWORD`          | The password of the user who owns the database     | None    | `openT00nix_Database_paSsW0rd`      | Yes      |
    | `DATABASE_PORT`              | The port where the database server is running      | None    | `3306`                              | Yes      |
    | `DATABASE_USERNAME`          | The name of the user who owns the database         | None    | `opentoonix_user`                   | Yes      |
    | `JWT_SECRET`                 | The secret used to sign and verify JSON Web Tokens | None    | `jWT_S3cR3T`                        | Yes      |

    <br>

    **Notes:**
    - All necessary environment variables must be set before running the application.
    - The database server must support `utf8` charset with `utf8_unicode_ci` collation.
    - Never give environment variable values to non-administrator users, since they could hack your server instance.

## Features

You can check the current state of features [here](https://github.com/Juansecu/OpenToonix-HTTP-Server/wiki/Features).

## Support

For further support, you can join us on [Discord](https://discord.gg/8ZWkyXnv4h).
