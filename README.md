# MoePicture-Android
`jskyzero` `2017/08/18`

本项目旨在为您提供更好的获取二次元图片的解决方案。

## TODO
> TRY TO FINISH IT
### v1.0
- [x] basic home page can show home post
- [x] basic sigle picure view and logic
- [x] basic simple notification
### v1.1
- [x] search function
- [x] picture info
- [x] hamburger menu
### v1.2
- [ ] search auto complete
- [ ] setting page
- [ ] farvate tags page

## What we are
```mermaid
graph TD;
    subgraph helpful_intermediary
    root[yande.re]
    web[Web pages]
    android(Android client)
    other[Other ways]
    root --> web
    root --> android
    root -.-> other
    web --> User
    android --> User
    other -.-> User
    end

    subgraph As_for_Android
    android2[Android client]
    moe((MoePicture))
    other2[Other clients]
    android2 --> moe
    android2 -.-> other2
    click moe "https://github.com/jskyzero/MoePicture-Android" "this is a link"
    style moe fill:#f0f0f0,stroke:#333,stroke-width:4px
    end
```

## LICENSE

GNU AFFERO GENERAL PUBLIC LICENSE Version 3