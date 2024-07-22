<template>
    <main ref="chart"></main>
</template>

<script>
import * as echarts from 'echarts/core';
import {
  DatasetComponent,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  TransformComponent
} from 'echarts/components';
import { LineChart } from 'echarts/charts';
import { LabelLayout, UniversalTransition } from 'echarts/features';
import { SVGRenderer } from 'echarts/renderers';
import api from '@/api/api';

echarts.use([
  DatasetComponent,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  TransformComponent,
  LineChart,
  SVGRenderer,
  LabelLayout,
  UniversalTransition
]);

export default {
    name: "LineChart",
    async mounted() {
        const { xList, yList, source } = await api.get("/api/chart/ranking/book");
        this.xList = xList.reverse(); // 反转顺序
        this.yList = yList.reverse(); // 反转顺序
        this.source = source;
        this.InitChart();
    },
    data() {
        return {
            xName: "月份",
            yName: "借阅量",
            xList: [],
            yList: [],
            source: []
        }
    },
    computed: {
        option() {
            const seriesList = [];

            this.xList.forEach(book => {
                const dataPoints = this.source.filter(item => item[1] === book).map(item => ({
                    value: [item[2], item[0]] // [yearMonth, orders]
                }));

                seriesList.push({
                    type: 'line',
                    data: dataPoints,
                    showSymbol: false,
                    name: book,
                    endLabel: {
                        show: true,
                        formatter: function (params) {
                            return params.value[1];
                        }
                    },
                    labelLayout: {
                        moveOverlap: 'shiftY'
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    encode: {
                        x: 0, // 对应 yearMonth
                        y: 1, // 对应 orders
                        label: ['book', 'orders'],
                        tooltip: ['orders']
                    }
                });
            });

            return {
                animationDuration: 5000,
                dataset: [{
                    source: this.source
                }],
                title: {
                    show: false,
                    left: 'center',
                    text: ''
                },
                tooltip: {
                    order: 'valueDesc',
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: this.yList // 使用 yList 中的时间数据
                },
                yAxis: {
                    name: this.yName,
                    nameLocation: 'end'
                },
                grid: {
                    right: 120,
                    bottom: 30
                },
                series: seriesList
            };
        }
    },
    methods: {
        InitChart() {
            let dom = this.$refs.chart;
            let chart = echarts.init(dom, null, {
                renderer: 'svg'
            });
            this.option && chart.setOption(this.option);
        }
    }
};
</script>

<style>
</style>
