<a name="readme-top"></a>
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<!-- ![DriverIcon](https://github.com/ChancePayne/AcmeDriverAssignment/assets/8661881/d5419d25-a9ed-4638-867e-36382a5560fb) -->

<div align="center">
  <a href="https://github.com/ChancePayne/AcmeDriverAssignment">
    <img src="https://github.com/ChancePayne/AcmeDriverAssignment/assets/8661881/d5419d25-a9ed-4638-867e-36382a5560fb" alt="Logo" width="80" height="80">
  </a>
<h3 align="center">Acme Driver Assignment</h3>
  <p align="center">
    Application that loads a list of drivers and shipments and calculates the most optimized driver assignments based on a top-secret algorithm.
    <br />
    <br />
    <a href="https://github.com/ChancePayne/AcmeDriverAssignment">View Demo</a>
    ·
    <a href="https://github.com/ChancePayne/AcmeDriverAssignment/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    ·
    <a href="https://github.com/ChancePayne/AcmeDriverAssignment/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>

</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#testing">Testing</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

### Built With

[![Android][android.com]][android-url] [![Kotlin][Kotlinlang.org]][kotlin-url] [![Compose][jetpack-compose]][compose-url] [![Retrofit][square-retrofit]][retrofit-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->

## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

* Android Studio

### Installation

1. Clone the repo

   ```sh
   git clone https://github.com/ChancePayne/AcmeDriverAssignment.git
   ```
2. Build with Android Studio

### Testing

In order to test this app with additional datasets, you can mock the endpoints used in the app. The app is setup for
mocking with Proxyman, but you can make adjustments necessary to use your favorite mocking software.

**Drivers**

```
GET https://cloud.chenzarchondie.xyz/s/CMs74xQrp99zw8y/download/drivers.json
```

The endpoint should return a list of strings to match the provided JSON structure.

```json
[
  "Dalinar Kholin",
  "Waxillium Ladrian"
]
```

**Shipments**

```
GET https://cloud.chenzarchondie.xyz/s/3wrz2fm6EexQbQE/download/shipments.json
```

The endpoint should also return a list of strings to match the provided JSON structure.

```json
[
  "7556 Greenholt Harbors, Lake Eduardotown, HI 28648-1463",
  "5168 Jerrold Fields #458, Muellerchester, WI 14539"
]
```

*This app uses a simple caching system, if you want to mock values be sure to close the app to ensure the mocked values
are used instead of the cache*

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->

## Usage

Launch the app, you will see a list of drivers. Select a driver's name to see their currently assigned shiment.

<div align="center">
    <img src="https://github.com/ChancePayne/AcmeDriverAssignment/assets/8661881/d791d6b0-b553-4099-b4ff-6f66fc1f5b5e" alt="List Screen" width="270" height="480">
    <img src="https://github.com/ChancePayne/AcmeDriverAssignment/assets/8661881/b7378ed6-47bb-4615-a0a9-4da29f8d8f2e" alt="List Screen" width="270" height="480">
</div>

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->

## Roadmap

- [ ] Migrate data storage and processing to server

- [ ] Improve local caching

- [ ] UI/UX updates
    - [ ] Integrate with a maps API to display location

- [ ] Shipment Details

    - [ ] Include additional shipment details for the driver

See the [open issues](https://github.com/ChancePayne/AcmeDriverAssignment/issues) for a full list of proposed features (
and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->

## Contact

Project Link: [https://github.com/ChancePayne/AcmeDriverAssignment](https://github.com/ChancePayne/AcmeDriverAssignment)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/chance-payne/

[product-screenshot]: images/screenshot.png

[Kotlinlang.org]: https://img.shields.io/badge/Kotlin-555?style=for-the-badge&logo=kotlin

[kotlin-url]: https://kotlinlang.org/

[android.com]: https://img.shields.io/badge/Android-555?style=for-the-badge&logo=android

[android-url]: https://android.com/

[jetpack-compose]: https://img.shields.io/badge/jetpack_compose-555?style=for-the-badge&logo=jetpackcompose

[compose-url]: https://developer.android.com/develop/ui/compose

[square-retrofit]: https://img.shields.io/badge/retrofit-555?style=for-the-badge&logo=square

[retrofit-url]: https://square.github.io/retrofit/
