# Digital Watch 4 — Installable Windows App

This project is configured to automatically build a **Windows installer (.msi)**.
People who download the finished installer do **not** need Java, a compiler, or any technical setup.

## What end users get

After publishing this project to GitHub, GitHub Actions creates:

```text
Digital-Watch-Windows-Installer.zip
```

Inside it is:

```text
Digital Watch-1.0.0.msi
```

Users download the `.msi`, double-click it, install the app, and launch **Digital Watch** from the Start Menu.

## Publish it

1. Create a free GitHub account.
2. Create a new public repository, for example `digital-watch`.
3. Upload **all files and folders** from this project, including the hidden `.github` folder.
4. Open the repository’s **Actions** tab.
5. Wait for **Build Windows Installer** to finish.
6. Download the artifact named **Digital-Watch-Windows-Installer**.
7. Share that ZIP, or create a GitHub Release and attach the `.msi` file.

## Notes

- This workflow creates a Windows `.msi` installer.
- `jpackage` bundles a Java runtime, so users do not need to install Java.
- For macOS or Linux installers, add equivalent workflow jobs running on `macos-latest` or `ubuntu-latest`.
- Add an `.ico` file and `--icon icon.ico` for a custom Windows application icon.
