//
//  AIDatabase.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 29/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "AIDatabase.h"
#define fileName @"AIDatabase.dat"

@implementation AIDatabase

+ (NSString *)filePath {
    NSURL *fileURL = [[NSURL URLWithString:PROJECT_DIR] URLByDeletingLastPathComponent];
    fileURL= [fileURL URLByAppendingPathComponent:fileName];
    return [fileURL absoluteString];
}

+ (void)saveData:(NSArray *)dataToStore {
    [NSKeyedArchiver archiveRootObject:dataToStore toFile:[AIDatabase filePath]];
}

+ (NSMutableArray *)loadData {
    NSMutableArray *dataToLoad = [NSKeyedUnarchiver unarchiveObjectWithFile:[AIDatabase filePath]];
    return dataToLoad;
}

@end
