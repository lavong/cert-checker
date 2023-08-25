# cert-checker

Script to conveniently print public key hashes (and more) of certificate chains for a given domain. For example:

```
$ cert-checker certs google.com
connecting to 'https://google.com'...

peer certificate chain:
  sha256/Qj5o5pUF1IVOU22irQ33ag3Ft/d6doqEm/VgbSpkjrM= | CN=www.google.com,O=Google LLC,L=Mountain View,ST=California,C=US
  sha256/YZPgTZ+woNCCCIW3LH2CxQeLzB/1m42QcCTBSdgayjs= | CN=GTS CA 1O1,O=Google Trust Services,C=US
  sha256/iie1VXtL7HzAMF+/PVPR9xzT80kQxdZeJ+zduCB3uj0= | CN=GlobalSign,O=GlobalSign,OU=GlobalSign Root CA - R2
```

Use `--verbose` to print whole certificates:

```
$ cert-checker certs google.com --verbose
```

Use `--proxy-host` and `--proxy-port` if you are sitting behind a proxy:
```
$ cert-checker certs --proxy-host localhost --proxy-port 8080 google.com
```

## Install

### MacOS
```
brew install lavong/repo/cert-checker
```
### Other

Download [latest release](https://github.com/lavong/cert-checker/releases/latest) and run `bin/cert-checker` or `bin/cert-checker.bat`

## Usage

```
$ cert-checker --help
Usage: cert-checker [OPTIONS] COMMAND [ARGS]...

Options:
  -h, --help  Show this message and exit

Commands:
  hello  Prints hello world a given amount of times
  certs  Prints certificates
```

```
$ cert-checker certs --help
Usage: cert-checker certs [OPTIONS] url

  Prints certificates

Options:
  -v, --verbose              Display verbose information
  -proxy, --proxy-host TEXT  Proxy hostname to use
  -port, --proxy-port INT    Proxy port to use
  -h, --help                 Show this message and exit

Arguments:
  url  The url to fetch
```

# License

    Copyright 2020 Lavong Soysavanh

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

