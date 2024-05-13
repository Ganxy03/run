<template>
  <div class="login-page" v-loading.fullscreen.lock="fullscreenLoading">
    <video autoplay loop muted playsinline class="background-video">
        <source src="../../demo/video/100部动漫角色联手拯救世界-在下蕾姆有何贵干-BV1XJ411Y7oK-C2GWdUaUskMa6m1j.mp4" type="video/mp4">
    </video>

    <div class="box">
        <div class="welcome">
            <div>
                <h2 style="font-size: 32px;">Welcome</h2>
                <h3>欢迎使用！</h3>
                <img style="border-radius: 50%;width: 260px;" src="@/assets/img/dog.jpg" alt="">
            </div>
        </div>
        <div class="container">
            <div>
                <img style="border-radius: 50%;width: 120px;" src="@/assets/img/dog.jpg" alt="">
                <h2 style="font-size: 32px;">Login</h2>
                <el-button @click="testAccount" type="text">测试账号</el-button>
                <div class="login-form">
                    <div class="input-group">
                        <label>用户名:</label>
                        <el-input v-model="loginForm.account"></el-input>
                    </div>
                    <div class="input-group">
                        <label>密码:</label>
                        <el-input type="password" v-model="loginForm.pwd"></el-input>
                    </div>
                    <div class="input-group">
                        <label >验证码:</label>
                        <el-input maxlength="4" v-model="loginForm.verify" style="height: 44px;">
                            <template #append>
                                <!-- <span style="font-size: 16px;">1234</span> -->
                                <img style="height: 44px;" :src="yan_picUrl" alt="验证码" />
                            </template>
                        </el-input>
                    </div>
                    <button @click="loginBtn" id="btn" >登录</button>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            loginForm: {
                account: '',
                pwd: '',
                verify: ''
            },
            yan_picUrl: '',
            yan_captcha: '',
            fullscreenLoading: false
        }
    },
    created() {
        this.generateCaptcha()
        this.verifyLogin()
    },
    methods: {
        verifyLogin() {
            const url = '/api/account/verify'
            const params = {
                account: localStorage.getItem('account'),
                token: localStorage.getItem('token')
            }
            axios.post(`${url}?account=${params.account}&token=${params.token}`, {} ,
                {
                    headers: {
                        'verifyCode': '2024'
                    }
                }
            ).then(res => {
                if(res.data == 'Valid identity') {
                    setTimeout(function() {
                        this.$router.push('/manager')
                        this.$message({
                            message: '已登录',
                            type: 'success'
                        })
                    }.bind(this), 1000)
                }
            }).catch(err => {
                this.fullscreenLoading = false;
                this.$message({
                    message: err,
                    type: 'warning'
                });
            })
        },
        testAccount() {
            this.loginForm.account = 'admin'
            this.loginForm.pwd = '@inc20240501.'
            this.loginForm.verify = this.yan_captcha
        },
        loginBtn() {
            if(this.loginForm.account == '') {
                this.$message({
                    message: '账号没填哦',
                    type: 'warning'
                })
            } else if(this.loginForm.pwd == '') {
                this.$message({
                    message: '密码没填哦',
                    type: 'warning'
                })
            } 
            // else if(this.loginForm.verify == '') {
            //     this.$message({
            //         message: '验证码没填哦',
            //         type: 'warning'
            //     })
            // }
            // else if(this.loginForm.verify != this.yan_captcha) {
            //     this.loginForm.verify = ''
            //     this.generateCaptcha()
            //     this.$message({
            //         message: '验证码错误',
            //         type: 'warning'
            //     })
            // }
            else {
                const url = '/api/account/login'
                const params = {
                    account: this.loginForm.account,
                    pwd: this.loginForm.pwd
                }
                this.fullscreenLoading = true
                axios.post(`${url}?account=${params.account}&pwd=${params.pwd}`, {},
                    {
                        headers: {
                            'verifyCode': '2024'
                        }
                    }
                ).then(res => {
                    // console.log(res)
                    if(res.data == 'Account not exited or pwd is wrong') {
                        setTimeout(function() {
                            this.fullscreenLoading = false
                            this.$message({
                                message: '账号不存在或密码错误',
                                type: 'warning'
                            })
                        }.bind(this), 1500)
                    } else {
                        localStorage.setItem('account', this.loginForm.account)
                        localStorage.setItem('token', res.data)
                        setTimeout(function() {
                            this.$router.push('/manager')
                            this.fullscreenLoading = false
                            this.$message({
                                message: '登录成功',
                                type: 'success'
                            })
                        }.bind(this), 1500)
                        
                        // throw new Error('接口异常，请联系管理员');
                    }
                }).catch(err => {
                    this.fullscreenLoading = false;
                    this.$message({
                        message: err,
                        type: 'warning'
                    });
                })
            }
        },
        generateCaptcha() {
            const img_w = 100;
            const img_h = 40;
            const char_len = 4;
            const font = '25px Arial';

            const char = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'.split('');
            let code = '';
            for (let i = 0; i < char_len; i++) {
            code += char[Math.floor(Math.random() * char.length)];
            }
            // console.log(code)
            const canvas = document.createElement('canvas');
            canvas.width = img_w;
            canvas.height = img_h;
            const ctx = canvas.getContext('2d');

            // 背景色
            ctx.fillStyle = 'black';
            ctx.fillRect(0, 0, img_w, img_h);

            // 画点
            for (let i = 0; i < 300; i++) {
            ctx.fillStyle = `rgba(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255}, 1)`;
            ctx.beginPath();
            ctx.arc(Math.random() * img_w, Math.random() * img_h, 1, 0, 2 * Math.PI);
            ctx.fill();
            }

            // 画线
            for (let i = 0; i < 10; i++) {
            ctx.strokeStyle = `rgba(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255}, 1)`;
            ctx.beginPath();
            ctx.moveTo(Math.random() * img_w, Math.random() * img_h);
            ctx.lineTo(Math.random() * img_w, Math.random() * img_h);
            ctx.stroke();
            }

            // 画矩形
            ctx.fillStyle = 'rgba(144, 144, 144, 0.8)';
            ctx.fillRect(0, 0, img_w, img_h);

            // 写文字
            ctx.font = font;
            ctx.textAlign = 'left';
            ctx.textBaseline = 'middle';
            ctx.fillStyle = `rgba(${Math.random() * 100}, ${Math.random() * 100}, ${Math.random() * 100}, 1)`;
            const textWidth = ctx.measureText(code).width;
            ctx.fillText(code, (img_w - textWidth) / 2, (img_h + parseInt(font) - 20) / 2);
            // ctx.fillText(code, (textWidth), (textWidth));

            this.yan_captcha = code
            // console.log(this.captcha)
            // console.log(canvas.toDataURL('image/png'));
            this.yan_picUrl = canvas.toDataURL('image/png')
        },
    }
}
</script>

<style>
.login-page {
    position: relative;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}
.login-page h2, .login-page h3 {
    margin: 6px 0 6px 0 ;
}
.login-page .background-video {
    position: fixed;
    top: 0;
    left: 0;
    min-width: 100%;
    min-height: 100vh;
    z-index: -1; /* 确保视频位于其他内容的下方 */
}
.login-page .box {
    width: 60%;
    height: 60vh;
    /* background-color: rgba(255, 255, 255, 0.4); */
    background-color: rgba(0, 0, 0, 0.6);
    position: relative;
    /* top: 20vh;
    left: 20%; */
    display: flex;
    overflow-y: auto;
}
.login-page .welcome {
    width: 50%;background-color: rgba(40, 191, 202, 0.6);color: #fff;display: flex;align-items: center;justify-content: center;
    height: 100%;
}
.login-page .container {
    width: calc(50% - 48px);min-height: 60vh;color: #fff;padding: 0 24px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.login-page .login-form {
    width: 100%;
    margin: 0 auto;
}
.login-page .input-group {
  margin-bottom: 1rem;
  display: flex;
}
.login-page .el-input-group__append {
    padding: 0;
}
.login-page label {
  width: 80px;
  line-height: 44px;
}

.login-page input {
  height: calc(44px - 12px);
  width: calc(100% - 24px - 48px);
  padding: 0;
  font-size: 1rem;
  /* border: 1px solid #ccc; */
  border-radius: 0.5rem;
}

/* .login-page input {
    
  width: calc(100% - 24px - 48px);
  padding: 0;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
} */

.login-page #btn {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
}
@media screen and (max-width: 976px) {
    .login-page .box {
        width: 90%;
    }
    .login-page .box .welcome {
        display: none;
    }
    .login-page .container {
        width: calc(100% - 48px);
    }
}
</style>