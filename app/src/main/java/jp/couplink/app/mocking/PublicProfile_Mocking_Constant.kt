package jp.couplink.app.mocking

import jp.couplink.R

/**
 * Created by BinhTran on 12/18/2017.
 */

class PublicProfile_Mocking_Constant {
    companion object {
        val name = "Matsu"
        val year = 25
        val location = "Tokyo"
        val status = "I'm working at my office"
        val common = "Saturday and Sunday"


        //common icon
        val common_image_path = intArrayOf(R.drawable.commonpoint_holiday)

        //image medium
        val image_path = arrayOf("/profiles/00000005/medium_e7c229dac9f62b884911a753261b0641f17f54a7059020e53a26813c03ac5291.jpg", "/profiles/00000002/medium_1dca8c218f01af4a541c72b790f968a8c1bd96f3f1094a76bfcec64db923bbc6.jpg", "/profiles/00000013/medium_99d9c87df2dc7f0f9bb558d8e3a6d77e218ba659c24611a0693bbd5104590066.jpg")
        val image_medium_path = "http://13.113.80.129:3000/profiles/00000006/medium_7be5312210a7b47f42362ce7c7b2b075bd808019f3224f82a84d85ed9ad8e9d3.jpg"

        //image small path
        val a = arrayOf("/profiles/00000002/small_14543253d93df0bd03bead992da23742f0356a3064355ed0b382de87515b001a.jpg", "/profiles/00000002/small_f0ea410a68793ec9897052e4954693adf2ec379887dfcf5ba1117ec88ee5e65a.jpg", "/profiles/00000012/small_80cb21212fc765a65379367ecf8a74efde3503b6757cd2fd00e3de266ecbfa4f.jpg")


        //Basic information
        val blood_type = "O"
        var brother = "Anh trai dau"

        //Nghe nghiep, ngoai hinh
        val job = "CNTT lien quan"
        val body_type = "Thong Thuong"
        val height = 175
        val education = "Cao dang"
        val country_home = "Quan Nagano"
        val quoctich = "Nhat Ban"

        //so thich, nhan vat, cuoc song
        val holiday_type = "Vao cuoi tuan"
        val Smoking_type = "khong hut thuoc"
        val drinking_type = "Doi khi uong"
        val live_type = "Song mot minh"
        val Sociable = "Tro thanh ban be dan"

        // Tinh yeu , hon nhan
        val married_schedule = "toi muon mot nguoi tot"
        val married_Status = "Doc than"
        val has_baby = "Khong co"
        val want_baby = "Co"
        val home_work = "Toi muon tham gia neu co the"
        val hope_event = "Sau khi dua ra cac thong diep"
        val price_first_event = "Thao luan voi doi phuong va quyet dinh"

    }


}
