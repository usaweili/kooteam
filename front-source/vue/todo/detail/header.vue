<template>
    <z-row class="header">
        <z-col :span="1"><i class="ft icon h1">&#xe740;</i></z-col>
        <z-col :span="23">
            <input :disabled="value.status === 1" type="text" maxlength="60" @change="change"
                   v-model="value.title"/>
            <div class="time">
                <z-nick :uid="value.uid"></z-nick>
                创建于：
                <z-idate :value="value._id"></z-idate>
            </div>
        </z-col>
    </z-row>
</template>
<script>
    export default {
        name: "detailHeader",
        props: ["value", 'type'],
        methods: {
            change() {
                let url = this.type === 1 ? "/patch/archive.json" : "/thing/patch.do";
                $.post({
                    title: this.value.title,
                    _id: this.value._id
                }, url, function (reback) {
                    $.emit("thingUpdate", this.value, "title");
                }, this);
            }
        }
    }
</script>