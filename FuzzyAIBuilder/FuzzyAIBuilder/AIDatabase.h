//
//  AIDatabase.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 29/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface AIDatabase : NSObject
+ (NSString *)filePath;
+ (void)saveData:(id)data;
+ (NSMutableArray *)loadData;
@end
