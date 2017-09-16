# Ethereum-Slots
The program allows the user to play slots and gamble their cryptocurrency. The java application only works with the Ethereum Testrpc and a loose ether account with Metamask on the localhost. This program was created by a team of 4 students with 2.5 months of dev time.

The `EtherGameDemo.mp4` shows the program working with the testrpc and Metamask extension.

##### To run and test the program, You need to have the Metamask extension installed on google and you need the Ethereum Testrpc.

1. Install the testrpc via npm. https://github.com/ethereumjs/testrpc

    `npm install -g ethereumjs-testrpc`

2. Intall Metamask on the chrome web store https://chrome.google.com/webstore/detail/metamask/nkbihfbeogaeaoehlefnkodbefgpgknn?hl=en

3. Create the test account on metamask first by importing the account's private key   
'0xae78473c4efa3795469085422d42a4de9eea61037c20eddc0930682bfedc8a26'   
![alt text](https://i.imgur.com/EBjC5ha.png)   
![alt text](https://i.imgur.com/IZq3GoT.png)   
![alt text](https://i.imgur.com/2aMTKEd.png)

4. Once testrpc is installed, run this command to start the Ethereum client   
    `testrpc --account="0xa8b7f177000b8193528eab77a0501b7e8b308297b2912bff8a43d9d8daf180a3,999999999999999999999" --account="0xae78473c4efa3795469085422d42a4de9eea61037c20eddc0930682bfedc8a26,10000000000000000000"`

5. On metamask, switch the network to `LocalHost8545`

6. Once the test account is filled with ether, run the `Game.jar` in the `dist` folder to play and test the game.



