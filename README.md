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
  1. **DATABASE_HOST** - The host where the database server is running
  2. **DATABASE_NAME** - The name of the database
  3. **DATABASE_PASSWORD** - The password of the user who owns the database
  4. **DATABASE_PORT** - The port where the database is running
  5. **DATABASE_USERNAME** - The name of the user who owns the database
  6. **JWT_SECRET** - The secret used to sign and verify JSON Web Tokens

  <br>

  **Notes:**
  - **Note 1:** All environment variables must be set before running the application.
  - **Note 2:** The database server must support `utf8` charset with `utf8_unicode_ci` collation.
  - **Note 3:** Never give environment variable values to non-administrator users, since they could hack your server instance.

## Support

For further support, you can join us on [Discord](https://discord.gg/8ZWkyXnv4h).
