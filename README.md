[![Build](https://github.com/julbme/gh-action-semver-build-vars/actions/workflows/maven-build.yml/badge.svg)](https://github.com/julbme/gh-action-semver-build-vars/actions/workflows/maven-build.yml)
[![Lint Commit Messages](https://github.com/julbme/gh-action-semver-build-vars/actions/workflows/commitlint.yml/badge.svg)](https://github.com/julbme/gh-action-semver-build-vars/actions/workflows/commitlint.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=julbme_gh-action-semver-build-vars&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=julbme_gh-action-semver-build-vars)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/julbme/gh-action-semver-build-vars)
# GitHub Action to compute SemVer release vars

The GitHub Action for computing SemVer build vars.

## Usage

### Example Workflow file

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Merge branch
        uses: julbme/gh-action-semver-build-vars@v1
        with:
          package_version: 1.0.0-SNAPSHOT
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

### Inputs

| Name    | Type   | Default      | Description                                                                                                                                                 |
| ------- | ------ | ------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `package_version`  | string | ` ` | The build version as set by package manager's file in the current branch. |

### Outputs

| Name   | Type   | Description                                                                       |
| ------ | ------ | --------------------------------------------------------------------------------- |
| `original_version`  | string | The build version. Ex: `1.0.0-SNAPSHOT` |
| `version`  | string | The build version. Ex: `1.0.0-unstable` |
| `sha_build_version`  | string | The version with the abbreviated sha as build token. Ex: `1.0.0-unstable+abcdef` |
| `sha_build_version_build`  | string | The abbreviated sha. Ex: `abcdef` |
| `timestamp_build_version`  | string | The version with the UTC timestamp as build token. Ex: `1.0.0-unstable+20220317160716` |
| `timestamp_build_version_build`  | string | The UTC timestamp. Ex: `20220317160716` |
| `timestamp_sha_build_version`  | string | The version with the UTC timestamp and the SHA as build token. Ex: `1.0.0-unstable+20220317160716.abcdef` |
| `timestamp_sha_build_version_build`  | string | The UTC timestamp and the SHA. Ex: `20220317160716.abcdef` |
| `docker_tag`  | string | The version with the abbreviated sha as build token. Ex: `1.0.0-unstable` |
| `docker_sha_build_tag`  | string | The version with the abbreviated sha as build token. Ex: `1.0.0-unstable+abcdef` |
| `docker_timestamp_build_tag`  | string | The version with the abbreviated sha as build token. Ex: `1.0.0-unstable+20220317160716` |
| `docker_timestamp_sha_build_tag`  | string | The version with the abbreviated sha as build token. Ex: `1.0.0-unstable+20220317160716.abcdef` |

## Contributing

This project is totally open source and contributors are welcome.