package cn.twoer.xiaoxiaole.algorithm;

import android.util.Log;

import java.util.Locale;

import cn.twoer.xiaoxiaole.box.BaseBox;

public class Algo {

    private static final String TAG = "Dong";

    public static boolean swapSucceed(BaseBox[][] boxes, int x1, int y1, int x2, int y2, int[][] move) {

        int result1 = check(boxes, x1, y1, move);
        int result2 = check(boxes, x2, y2, move);

        Log.e(TAG, "swapSucceed() ended with: result1 = [" + result1 + "], result2 = [" + result2 + "]");

        if (result1 > 0) {
            award(boxes, result1, x1, y1, move);
            dumpArray(move);
            goDwon(move, boxes);
        }
        if (result2 > 0) {
            award(boxes, result2, x2, y2, move);
            dumpArray(move);
            goDwon(move, boxes);
        }

        return !(result1 == 0 && result2 == 0);
    }

    static void goDwon(int[][] b, BaseBox[][] a) {
        int high = a.length;
        int wide = a[0].length;
        int moveSize = 0;

        for (int i = high - 1; i >= 0; i--) {
            for (int j = 0; j < wide; j++) {
                moveSize = b[i][j];
                //移动格数>0，向下移动
                if (moveSize > 0) {

                    a[(i + moveSize)][j].mType = a[i][j].mType;
                    a[i][j].mType = 0;
                }

            }
        }
    }

    public static boolean self(BaseBox[][] a, int[][] b) {

        int high = a.length;
        int wide = a[0].length;
        int result1 = 0;

        for (int i = 0; i < high; i++) {
            for (int j = 0; j < wide; j++) {
                result1 = check(a, i, j, b);
                if (result1 > 0) {
                    award(a, result1, i, j, b);
                    goDwon(b, a);
                    return true;
                }
            }
        }
        return false;
    }


//    public static void main(String[] args) {
//        int high = 0, wide = 0;
//        initA(high, wide);
//
//        //两个交换元素的坐标
//        int ax = -1, ay = -1;//当前选中的坐标，初始化为 -1（没有选中的）；
//        int bx = 0, by = 0;//用户选中的坐标，待读取！！！
//
//        while (true) {
//
//            //选一个坐标
//
//            //判断坐标合法
//            if (bx < 0 || by < 0)
//                continue;
//
//            //检查选中的两个坐标是否相邻
//            if (ax == bx && ay - by == 1 || by - ay == 1) {
//
//                //两个元素交换
//                int num = a[ax][ay];
//                a[ax][ay] = a[bx][by];
//                a[bx][by] = num;
//
//                //分别以a、b作为中心点检查可消除的情况
//                int aa = check(ax, ay);
//                int bb = check(bx, by);
//
//                //如果都不可以消除，就再把两个元素交换回去
//                if (aa == 0 && bb == 0) {
//                    num = a[ax][ay];
//                    a[ax][ay] = a[bx][by];
//                    a[bx][by] = num;
//                } else if (aa != 0) {
//                    award(aa, ax, ay);
//                } else if (bb != 0) {
//                    award(bb, bx, by);
//                }
//
//                //奖励完后，为0的位置等同为空，上方元素可掉落，填补满表
//                fillA();
//            }
//
//            //检查是否相邻
//            if (ay == by && ax - bx == 1 || bx - ax == 1) {
//
//                //两个元素交换
//                int num = a[ax][ay];
//                a[ax][ay] = a[bx][by];
//                a[bx][by] = num;
//
//                //分别以a、b作为中心点检查可消除的情况
//                int aa = check(ax, ay);
//                int bb = check(bx, by);
//
//                //如果都不可以消除，就再把两个元素交换回去
//                if (aa == 0 && bb == 0) {
//                    num = a[ax][ay];
//                    a[ax][ay] = a[bx][by];
//                    a[bx][by] = num;
//                } else if (aa != 0) {
//                    award(aa, ax, ay);
//                } else if (bb != 0) {
//                    award(bb, bx, by);
//                }
//
//                //奖励完后，为0的位置等同为空，上方元素可掉落，填补满表
//                fillA();
//            }
//
//
//            //不相邻则无反应，记录后选的那个为当前选中的坐标
//            ax = bx;
//            ay = by;
//
//        }
//
//
//    }

    private static void fillA() {
        //先降落？

        //空缺位置随机生成
    }

    //消除 = 3，中心点置0清空
    //消除 > 3，中心点位置替换为道具奖励的值
    private static void award(BaseBox[][] a, int num, int x, int y, int[][] b) {
        if (num == 3) {
            a[x][y].mType = 0;//中心点置0清空
            b[x][y] = 0;//中心点移动位置清空

            // == 3是，该位置正上方的所有位置移动格数+1
            for (int i = 1; i <= x; i++) {
                if (a[x - i][y].mType != 0) {
                    b[x - i][y]++;
                }
            }

        }
        if (num == 4)
            a[x][y].mType = 444;
        if (num == 5)
            a[x][y].mType = 555;
        if (num > 5)
            a[x][y].mType = 666;
    }


    private static void initA(int x, int y) {

    }


    //传入中心点的坐标，检查可消除的情况
    private static int check(BaseBox[][] boxes, int centerX, int centerY, int move[][]) {
        int height = boxes.length;
        int width = boxes[0].length;

        int centerType = boxes[centerX][centerY].mType;

        int up, down, left, right;

        //先算上
        int i = 1;
        up = 0;
        while (centerX - i >= 0 && centerType == boxes[centerX - i][centerY].mType) {
            up = up + 1;
            i = i + 1;
            Log.e(TAG, "上");
        }

        //下
        i = 1;
        down = 0;
        while (centerX + i < height && centerType == boxes[centerX + i][centerY].mType) {
            down = down + 1;
            i = i + 1;
            Log.e(TAG, "下");

        }

        //左
        i = 1;
        left = 0;
        while (centerY - i >= 0 && centerType == boxes[centerX][centerY - i].mType) {
            left = left + 1;
            i = i + 1;
            Log.e(TAG, "左");
        }

        //右
        i = 1;
        right = 0;
        while (centerY + i < width && centerType == boxes[centerX][centerY + i].mType) {
            right = right + 1;
            i = i + 1;
            Log.e(TAG, "右");

        }

        //横竖都可以消除
        if (left + right + 1 >= 3 && up + down + 1 >= 3) {
            //left
            for (i = 1; i <= left; i++) {
                //可消除的位置至0，以便清空
                boxes[centerX][centerY - i].mType = 0;

                //正上方所有位置移动格数+1
                for (int j = 1; j <= centerX; j++) {
                    if (boxes[centerX - j][centerY - i].mType != 0) {
                        move[centerX - j][centerY - i]++;
                    }
                }
            }

            //Right
            for (i = 1; i <= right; i++) {
                //可消除的位置至0，以便清空
                boxes[centerX][centerY + i].mType = 0;

                //正上方所有位置移动格数+1
                for (int j = 1; j <= centerX; j++) {
                    if (boxes[centerX - j][centerY + i].mType != 0)
                        move[centerX - j][centerY + i]++;
                }
            }

            //move[centerX][centerY] ++;中心点不会掉落

            //up
            for (i = 1; i <= up; i++) {
                boxes[centerX - i][centerY].mType = 0;
            }

            //位置正上方的位置移动格数+up + down
            for (int j = i; j <= centerX; j++) {
                if (boxes[centerX - j][centerY].mType != 0) {
                    move[centerX - j][centerY] += (up + down);
                }
            }

            //down
            for (i = 1; i <= down; i++) {
                boxes[centerX + i][centerY].mType = 0;
            }

            move[centerX][centerY] += down;


            //返回消除数量，以确定道具奖励
            return left + right + up + down + 1;
        }

        //横可消
        if (left + right + 1 >= 3) {
            Log.e(TAG, "横可消");

            for (i = 1; i <= left; i++) {
                //可消除的位置至0，以便清空
                boxes[centerX][centerY - i].mType = 0;

                //正上方所有位置移动格数+1
                for (int j = 1; j <= centerX; j++) {
                    if (boxes[centerX - j][centerY - i].mType != 0) {
                        move[centerX - j][centerY - i]++;
                    }
                }

            }

            for (i = 1; i <= right; i++) {
                //可消除的位置至0，以便清空
                boxes[centerX][centerY + i].mType = 0;

                //正上方所有位置移动格数+1
                for (int j = 1; j <= centerX; j++) {
                    if (boxes[centerX - j][centerY + i].mType != 0)
                        move[centerX - j][centerY + i]++;
                }

            }

            //move[centerX][centerY] += 1;

            //返回消除数量，以确定道具奖励
            return left + right + 1;
        }

        //竖可消
        if (up + down + 1 >= 3) {
            Log.e(TAG, "竖可消");

            //可消除的位置至0，以便清空
            for (i = 1; i <= up; i++) {
                boxes[centerX - i][centerY].mType = 0;
            }


            //位置正上方的位置移动格数+up + down
            for (int j = i; j <= centerX; j++) {
                if (boxes[centerX - j][centerY].mType != 0) {
                    move[centerX - j][centerY] += (up + down);
                }
            }
            //move[centerX][centerY] += (up + down);

            for (i = 1; i <= down; i++) {
                boxes[centerX + i][centerY].mType = 0;
            }
            move[centerX][centerY] += down;

            //返回消除数量，以确定道具奖励
            return up + down + 1;
        }

        //都消不了，返回0
        return 0;
    }


    public static void dumpArray(BaseBox[][] boxes) {
        StringBuilder sb = new StringBuilder();
        for (BaseBox[] boxLine : boxes) {
            sb.append("\n");
            for (BaseBox box : boxLine) {
                sb.append(String.format(Locale.getDefault(), "%3d", box.mType)).append(",");
            }
        }
        Log.e(TAG, "\n" + sb.toString());
    }

    public static void dumpArray(int[][] boxes) {
        StringBuilder sb = new StringBuilder();
        for (int[] boxLine : boxes) {
            sb.append("\n");
            for (int box : boxLine) {
                sb.append(String.format(Locale.getDefault(), "%3d", box)).append(",");
            }
        }
        Log.e(TAG, "...\n" + sb.toString());
    }
}
