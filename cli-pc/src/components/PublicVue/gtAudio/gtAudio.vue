<template>
  <div class="gt_audio">
    <div class="audioPlayer">
        <div class="audioDisabled"></div>
        <audio :src="audioSrc" @play="play($event)" @pause="pause($event)" @ended="ended($event)" @canplaythrough="audioThrough($event)" ></audio><div class="audioButton">
        <img v-on:click="audioPlay($event)" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABW0lEQVRYR+2WwU3DMBiF328GYI6KZbiCixRPQLpBskG6AZWAc9ggbNAe0vQYNmgPzSFV/aNSaKBSieM6CQd8jv2+PL3324SeF/Wsj78PMHjma6Fxz4SkKDHOFS1dulbrwNUT7wQvP0QZSy2gsluKXUGYAPCxGANxUUK5cMMK4OAGIcgkjc9xwx7gS5WRbC+gFjeU24CcD/CpykAwlxQ2hXAGsM8oplpjtLijxBTEKcBBlBGtNwhNQtoOwN6NnAmjusq2BlBl9PfKtg5QV9luAKqmPMwlqe8B7RRgJ5xK+qHZLQBjkg7J68OBlWYE2ZCi4/nQugMMvGgB/9SobhNgpQleP3OAMVlv4Hc+CRl40xpeL3cBA2FRIjL5a7ctYMxA8FJJU9Mb0BXAyWo1AbFtwetWwLN9BTV1oHoVA0bVcurA4JF9QQjAiE2r5RSgyWE239ZmwObQJnv+Ad4Byr7EIY/gRmUAAAAASUVORK5CYII=">
    </div><section class="audioProgressBar" v-on:click="audioSkip($event)"><div class="audioProgress"><div class="audioSpeed"></div></div><div class="audioIconDiv">
        <img class="audioIcon" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAYAAACpSkzOAAACyElEQVRIib2Wz2sTQRTHP7v50aapuoI/KGpBRCoIBUNBqZBLz+lBFA85eezFozm09CL0oP4BXtNjD0LB9uDJ2JOHNiDYoqXQxdaGkkMMTbPJTnY9ZEYnMYnbRnww7OTHvM9833vzdgyCmdH2VOZ3mXd18DfnBmBqc925D3javCewE8QEwsDA9PT0Gdu2U5VK5XW9Xn/vum7ecZx35XL5xebmZhKIAVH5f7WhQJCQXBizbfuREGLH72H1ev3j+vr6PQmMBIEpyAAQL5VKL33f93pBlHmeV9nd3U0DQ3KToW4wXcnw/v7+syCANhNbW1sPNFhHZaaUHV9eXp7wPM89BcgXQhQymcyoFsYWkFIzCFilUunNaSDK9vb2ngPnZApaQqjUDKdSqVHP8477ATmO8wW4AMTbVZlKTS6Xe9wPRNnMzMwYcFblytRCZwJhy7Ku9SrLoDY5OTkqw2YChn7aTcAUQpg91gc213VNDYSuCMAoFAqH/wK0srJySIeKiwDDwOVEIjHeaDSq/eSnUqnsADeAizTPVEgpUo3Q39jYOC4UCrl+1Gxvb7/ld6MFmjFUqkI0m2IkEol8m5qaemgYxonzVavViul0eta27R+AA7gSCmjlDVwFbq+trb06acg8z2ssLi4+BW4BI2jlrYOiNPN0iWZ87+Tz+awfsKkKIapLS0uzwDhwnS4HtqUFAVeAMWAim81mjo6OvveCFIvFz3Nzc0+ABHBTqmlpQXr5qbqPSuCQ3FHcsqz4wsLC3WQyed+yrJFYLHbecZzywcHB19XV1Q/z8/OfgCpQkeNY5kfI4esgowNskGYXjsnd6S81H2gAdaAmHVflaIEAflgDqXc/slKQjoR0Fu0CcjVYTc6F/O3XHaLTG1BXFpLOw9pTP3uetnNXm7dAuoHU9zpQB+trPDkacqjPf9yG/tt1K9C1qA2qW+AL5E9dShbfB+cqswAAAABJRU5ErkJggg==">
    </div></section><div class="audioTime">00:00</div></div>
  </div>
</template>
<script>
var timerCurrent;
export default {
  props: {
    src: String
  },
  data() {
    return {
      audioSrc: null
    }
  },
  mounted() {
    this.audioSrc = this.src
  },
  methods: {
    //播放事件
    play(evt) {
        let _self = evt.target
        let _main = _self.parentNode;
        let _prgWidth = _main.getElementsByClassName("audioProgressBar")[0].offsetWidth;
        _self.setAttribute("data-play", "true");
        timerCurrent = setInterval(function() {
            _main.getElementsByClassName("audioTime")[0].innerHTML = timeFormat(_self.currentTime);
            _main.getElementsByClassName("audioIcon")[0].style.left = _self.currentTime / _self.duration * (_prgWidth - 20) + "px";
            _main.getElementsByClassName("audioSpeed")[0].style.width = _self.currentTime / _self.duration * (_prgWidth - 10) + "px";
        }, 500);
    },
    //停止事件
    pause(evt) {
        evt.target.setAttribute("data-play", "false");
        window.clearInterval(timerCurrent);
    },
    //播放结束
    ended(evt) {
        let _self = evt.target
        let _main = _self.parentNode;
        let playImage = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABW0lEQVRYR+2WwU3DMBiF328GYI6KZbiCixRPQLpBskG6AZWAc9ggbNAe0vQYNmgPzSFV/aNSaKBSieM6CQd8jv2+PL3324SeF/Wsj78PMHjma6Fxz4SkKDHOFS1dulbrwNUT7wQvP0QZSy2gsluKXUGYAPCxGANxUUK5cMMK4OAGIcgkjc9xwx7gS5WRbC+gFjeU24CcD/CpykAwlxQ2hXAGsM8oplpjtLijxBTEKcBBlBGtNwhNQtoOwN6NnAmjusq2BlBl9PfKtg5QV9luAKqmPMwlqe8B7RRgJ5xK+qHZLQBjkg7J68OBlWYE2ZCi4/nQugMMvGgB/9SobhNgpQleP3OAMVlv4Hc+CRl40xpeL3cBA2FRIjL5a7ctYMxA8FJJU9Mb0BXAyWo1AbFtwetWwLN9BTV1oHoVA0bVcurA4JF9QQjAiE2r5RSgyWE239ZmwObQJnv+Ad4Byr7EIY/gRmUAAAAASUVORK5CYII=';
        _self.setAttribute("data-play", "false");
        _self.pause();
        _self.currentTime = 0;
        _main.parentNode.getElementsByClassName("audioButton")[0].getElementsByTagName("img")[0].setAttribute("src", playImage);
        _main.parentNode.getElementsByClassName("audioTime")[0].innerHTML = '00:00';
        _main.parentNode.getElementsByClassName("audioIcon")[0].style.left = "0px";
        _main.parentNode.getElementsByClassName("audioSpeed")[0].style.width = "0px";
    },
    //开始播放
    audioPlay(evt) {
        let ado = evt.target.parentNode.previousSibling
        let adoList = document.getElementsByTagName("audio")
        let playImage = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABW0lEQVRYR+2WwU3DMBiF328GYI6KZbiCixRPQLpBskG6AZWAc9ggbNAe0vQYNmgPzSFV/aNSaKBSieM6CQd8jv2+PL3324SeF/Wsj78PMHjma6Fxz4SkKDHOFS1dulbrwNUT7wQvP0QZSy2gsluKXUGYAPCxGANxUUK5cMMK4OAGIcgkjc9xwx7gS5WRbC+gFjeU24CcD/CpykAwlxQ2hXAGsM8oplpjtLijxBTEKcBBlBGtNwhNQtoOwN6NnAmjusq2BlBl9PfKtg5QV9luAKqmPMwlqe8B7RRgJ5xK+qHZLQBjkg7J68OBlWYE2ZCi4/nQugMMvGgB/9SobhNgpQleP3OAMVlv4Hc+CRl40xpeL3cBA2FRIjL5a7ctYMxA8FJJU9Mb0BXAyWo1AbFtwetWwLN9BTV1oHoVA0bVcurA4JF9QQjAiE2r5RSgyWE239ZmwObQJnv+Ad4Byr7EIY/gRmUAAAAASUVORK5CYII=';
        let shopImage = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAjUlEQVRYR2NkGGDAOMD2M4w6gKgQ0Fj834GBmeE/KLqY/zN8vBrNeAFb1Gkv/W/wl5GBHyZ3I4rxIKEoJsoB2kv/gy2HgoNXoxkdcDjgAAMDgz1M7mo0I0HzCSoAGTbqgNEQGA2B0RAYDYHREBgNgdEQGA2B0RAY8BAg1LSmRJ6oVjElFhDSO+qAAQ8BAPnb0iFyumREAAAAAElFTkSuQmCC';
        for (let i = 0; i < adoList.length; i++) {
            evt.target.setAttribute("src", playImage)
            adoList[i].pause()
        }
        if (ado.getAttribute("data-play") != 'true') {
          evt.target.setAttribute("src", shopImage)
          ado.play()
        }
    },
    //加载完成
    audioThrough(evt) {
        let _self = evt.target;
        _self.parentNode.getElementsByClassName("audioDisabled")[0].style.display = "none";
    },
    //跳转进度条
    audioSkip(evt) {
        let e = event || window.event;
        let ado = evt.target.parentNode.parentNode.parentNode.getElementsByTagName("audio")[0];
        let pbWidth = evt.target.offsetWidth;
        let speed = e.offsetX - ado.parentNode.getElementsByClassName("audioIcon")[0].offsetWidth / 2;
        let scale = (speed / pbWidth + 0.05).toFixed(2);

        if(scale > 0) {
          ado.parentNode.getElementsByClassName("audioIcon")[0].style.left = speed + "px";
          ado.parentNode.getElementsByClassName("audioSpeed")[0].style.width = e.offsetX - 3 + "px";
          ado.parentNode.getElementsByClassName("audioTime")[0].innerHTML = timeFormat(ado.duration * scale);
          ado.currentTime = ado.duration * scale;
        }
    }
  }
}

//格式化播放时间
function timeFormat(time) {
    let minutes = parseInt(time / 60);
    if (minutes < 10) {
        minutes = "0" + minutes;
    }

    var seconds = parseInt(time % 60);
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    return minutes + ":" + seconds;
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style type="text/css">
.gt_audio{
    display: inline-block;
}

.audioDisabled {
    position: absolute;
    width: 300px;
    height: 36px;
    background: #fff;
    opacity: 0;
    filter: alpha(opacity=0);
    z-index: 5;
}

.audioPlayer {
    width: 300px;
    height: 36px;
    border: 1px solid #dfe6ec;
    border-radius: 5px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 10px 0;
    user-select: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
}

.audioPlayer .audioButton {
    flex: 1;
    display: inline-block;
    text-align: center;
}

.audioPlayer .audioButton>img {
    width: 24px;
    vertical-align: middle;
}

.audioPlayer .audioProgressBar {
    flex: 5;
    display: inline-block;
    height: 100%;
}

.audioProgress {
    width: 95%;
    height: 3px;
    background-color: #999;
    border-radius: 1px;
    text-align: right;
    float: right;
    margin-top: 15px;
}

.audioPlayer .audioProgressBar .audioSpeed {
    width: 0%;
    height: 3px;
    background-color: #2ba5ff;
}

.audioPlayer .audioProgressBar .audioIconDiv {
  position: absolute;
}

.audioPlayer .audioProgressBar .audioIcon {
    width: 26px;
    position: relative;
    left: 0px;
    top: 5px;
}

.audioPlayer .audioTime {
    flex: 1.3;
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    color: #999;
    font-size: 15px;
}
</style>
